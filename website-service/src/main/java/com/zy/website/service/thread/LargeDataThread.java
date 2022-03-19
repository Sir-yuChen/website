package com.zy.website.service.thread;


import com.zy.website.model.FilmModel;
import com.zy.website.service.FilmService;
import com.zy.website.utils.spring.ApplicationContextUtil;
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
        long startTime = System.currentTimeMillis();    //获取开始时间
        int failCount = 0;
        int successCount = 0;
        try {
            while (queue.size() > 0) {
                FilmModel model = queue.poll(5, TimeUnit.SECONDS);
                logger.info("线程池 job  {}线程执行任务线程ID{}",Thread.currentThread().getName(),Thread.currentThread().getId());    //当前线程
                if (Optional.ofNullable(model).isPresent()) {
                    int i = updateName(model);
                    if (i == 0) {
                        successCount += 1;
                    } else {
                        failCount += 1;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("线程池 job  异常信息：", e);
            Thread.currentThread().interrupt();
        }
        long endTime = System.currentTimeMillis();    //获取结束时间
        logger.info("线程池 job  耗时：" + (endTime - startTime) / 1000 + "s");    //输出程序运行时间
        logger.info("线程池 job  执行成功{}次", successCount);
        return failCount;
    }

    private int updateName(FilmModel model) {
        try {
            FilmService filmService = ApplicationContextUtil.getBean(FilmService.class);
           /* boolean queue = model.getFilmName().contains("QUEUE");
            if (queue) {
                model.setFilmName(model.getFilmName().substring(0,model.getFilmName().indexOf("QUEUE")));
            }*/
            model.setCreator("ZHANG-YU");
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
