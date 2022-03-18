package com.zy.website.service.thread;


import com.zy.website.model.FilmModel;
import com.zy.website.service.FilmService;
import com.zy.website.utils.ioc.ApplicationContextUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class LargeDataThread implements Callable<Integer> {

    private static Logger logger = LogManager.getLogger(LargeDataThread.class);

    private BlockingQueue<FilmModel> queue;

    public LargeDataThread(BlockingQueue<FilmModel> queue) {
        this.queue = queue;
    }

    @Override
    public Integer call() throws Exception {
        int failCount = 0;
        try {
            while (queue.size() > 0) {
                FilmModel model = queue.poll(5, TimeUnit.SECONDS);
                if (Optional.ofNullable(model).isPresent()) {
                    failCount += updateName(model);
                }
            }
        } catch (Exception e) {
            logger.error("线程池 job  异常信息：", e);
            Thread.currentThread().interrupt();
        }
        return failCount;
    }

    private int updateName(FilmModel model) {
        try {
            FilmService filmService = ApplicationContextUtil.getBean(FilmService.class);
            model.setFilmName(model.getFilmName() + "QUEUE");
            boolean b = filmService.updateById(model);
            if (!b) {
                return 1;
            }
            return 0;
        } catch (Exception e) {
            logger.error("线程池 job  异常信息：", e);
        }
        return 1;
    }
}
