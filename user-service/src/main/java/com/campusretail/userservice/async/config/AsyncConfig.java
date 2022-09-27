package com.campusretail.userservice.async.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Confiuration class for the executor to make
 * all the methods async
 */

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean("AsyncExecutor")
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(75);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }

}
