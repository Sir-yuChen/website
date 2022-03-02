package com.zy.website.jobCompleteHandler;

import com.github.ltsopensource.core.domain.JobResult;
import com.github.ltsopensource.jobclient.support.JobCompletedHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 任务完成处理类 定时任务完成后做的事情
 */
@Component
public class JobCompletedHandlerImpl implements JobCompletedHandler {

    private static Logger logger = LogManager.getLogger(JobCompletedHandlerImpl.class);

    @Override
    public void onComplete(List<JobResult> jobResults) {
        //对任务执行结果进行处理 打印相应的日志信息
        if (CollectionUtils.isNotEmpty(jobResults)) {
            for (JobResult jobResult : jobResults) {
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                logger.info("任务执行完成taskId={}, 执行完成时间={}, job={}",
                        jobResult.getJob().getTaskId(), time, jobResult.getJob().toString());
            }
        }
    }

}