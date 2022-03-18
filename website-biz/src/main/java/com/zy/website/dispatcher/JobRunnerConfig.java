package com.zy.website.dispatcher;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class JobRunnerConfig {

    private int corePoolSize = 5;

    private int maxPoolSize = 5;

    @Bean
    public ThreadPoolTaskExecutor testTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setKeepAliveSeconds(600);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setQueueCapacity(0);
        executor.setThreadNamePrefix("线程测试[TestTaskExecutor]--");
        return executor;
    }
}
