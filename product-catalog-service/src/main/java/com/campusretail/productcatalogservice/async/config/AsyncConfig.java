package com.campusretail.productcatalogservice.async.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Class to configure the basics of the executor
 * for the Async methods in the API
 */

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
