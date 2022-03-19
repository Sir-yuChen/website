package com.zy.website.taskTracker;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import com.zy.website.service.ProxyIpService;
import com.zy.website.utils.spring.ApplicationContextUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JobRunnerProxyIpPool implements JobRunner {
    private static Logger logger = LogManager.getLogger(JobRunnerProxyIpPool.class);

    //秒杀活动初始化数据定时任务
    @Override
    public Result run(JobContext jobContext){
        logger.info("获取可用代理IP JOB 开始");
        try {
            ProxyIpService proxyIpService = ApplicationContextUtil.getBean(ProxyIpService.class);
            proxyIpService.getProxyJob();
            logger.info("获取可用代理IP JOB 完成");
        }catch (Exception e){
            logger.error("获取可用代理IP JOB 执行异常",e);
            return new Result(Action.EXECUTE_FAILED, "获取可用代理IP JOB任务，执行失败");
        }
        return new Result(Action.EXECUTE_SUCCESS, "获取可用代理IP JOB任务，执行成功");
    }
}
