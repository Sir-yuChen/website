package com.zy.website.taskTracker;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.code.ApiReturnCode;
import com.zy.website.service.FilmService;
import com.zy.website.utils.spring.ApplicationContextUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JobRunnerTopFilm implements JobRunner {
    private static Logger logger = LogManager.getLogger(JobRunnerFilm.class);

    //秒杀活动初始化数据定时任务
    @Override
    public Result run(JobContext jobContext){
        logger.info("视频信息Top250加载 JOB 开始");
        try {
            FilmService filmService = ApplicationContextUtil.getBean(FilmService.class);
            ApiReturn apiReturn = filmService.refreshTop250FilmData();
            if (!apiReturn.getCode().equals(ApiReturnCode.SUCCESSFUL.getCode())) {
                return new Result(Action.EXECUTE_FAILED, apiReturn.getMsg());
            }
        }catch (Exception e){
            logger.error("视频信息Top250加载 JOB 执行异常",e);
            return new Result(Action.EXECUTE_FAILED, "视频信息Top250加载 JOB任务，执行失败");
        }
        return new Result(Action.EXECUTE_SUCCESS, "视频信息Top250加载 JOB任务，执行成功");
    }
}
