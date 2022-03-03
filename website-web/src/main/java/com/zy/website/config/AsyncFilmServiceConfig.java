package com.zy.website.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 *  线程池配置        @Async("asyncOrderService")
 *  https://blog.csdn.net/weixin_43553153/article/details/119746468
 **/
@Setter
@ConfigurationProperties(prefix = "async.film-job")
@EnableAsync
@Configuration
public class AsyncFilmServiceConfig {
    /**
     * 核心线程数（默认线程数）
     */
    private int corePoolSize;
    /**
     * 最大线程数
     */
    private int maxPoolSize;
    /**
     * 允许线程空闲时间（单位：默认为秒）
     */
    private int keepAliveSeconds;
    /**
     * 缓冲队列大小
     */
    private int queueCapacity;
    /**
     * 线程池名前缀
     */
    private String threadNamePrefix;

    @Bean
    public ThreadPoolTaskExecutor asyncFilmService() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setThreadNamePrefix(threadNamePrefix);
        // 线程池对拒绝任务的处理策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 完成任务自动关闭 , 默认为false
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 核心线程超时退出，默认为false
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
