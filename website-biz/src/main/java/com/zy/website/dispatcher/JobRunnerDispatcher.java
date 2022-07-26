package com.zy.website.dispatcher;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.spring.boot.annotation.JobRunner4TaskTracker;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import com.zy.website.taskTracker.JobRunnerFilm;
import com.zy.website.taskTracker.JobRunnerProxyIpPool;
import com.zy.website.taskTracker.JobRunnerTestBigData;
import com.zy.website.taskTracker.JobRunnerTopFilm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

@JobRunner4TaskTracker //注解必须有，才能起到分发的作用
public class JobRunnerDispatcher implements JobRunner {

    private static Logger logger = LogManager.getLogger(JobRunnerDispatcher.class);

    public static final String JOB_TYPE = "type";
    private static final ConcurrentHashMap<String/*type*/, JobRunner> JOB_RUNNER_MAP = new ConcurrentHashMap<String, JobRunner>();

    static {
//        JOB_RUNNER_MAP.put("loadFilmInfo", ApplicationContextUtil.getBean(JobRunnerFilm.class));
        JOB_RUNNER_MAP.put("loadFilmInfo",new JobRunnerFilm());
        JOB_RUNNER_MAP.put("loadTopFilmInfo",new JobRunnerTopFilm());
        JOB_RUNNER_MAP.put("testBigData",new JobRunnerTestBigData());
        JOB_RUNNER_MAP.put("getProxyIp",new JobRunnerProxyIpPool());
    }
    @Override
    public Result run(JobContext jobContext) throws Throwable {
        try {
            //根据type选择对应的JobRunner运行
            Job job = jobContext.getJob();
            String taskType= job.getParam(JobRunnerDispatcher.JOB_TYPE);
            logger.info("正在匹配对应任务.........");
            JobRunner jobRunner = JOB_RUNNER_MAP.get(taskType);
            logger.info(" 进入任务中......... TaskType："+taskType);
            return jobRunner.run(jobContext);
        } catch (Exception e) {
            logger.info("Run job failed!", e);
            return new Result(Action.EXECUTE_LATER, e.getMessage());
        }
        //return new JobDispatcher().run(jobContext);
    }
}
