package com.campusretail.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Async configuration class for
 * the executor to make all the methods
 * asynchronous
 */
@Configuration
@EnableAsync
public class AsyncConfig {
	@Bean(name = "asyncExecutor")
	public Executor asyncExecutor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(4);
		executor.setQueueCapacity(75);
		executor.setThreadNamePrefix("AsyncThread-");
		executor.initialize();
		return executor;
	}
}
