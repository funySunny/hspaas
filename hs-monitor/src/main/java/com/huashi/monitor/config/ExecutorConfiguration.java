package com.huashi.monitor.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 
 * TODO 线程池配置
 * 
 * @author zhengying
 * @version V1.0
 * @date 2017年9月20日 下午5:58:30
 */
@Configuration
@Order(1)
public class ExecutorConfiguration {
	
	/**
	 * 配置线程池
	 * 
	 * @return
	 */
	@Bean(name = "threadPoolTaskExecutor")
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {

		ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();

		// 线程池所使用的缓冲队列
		poolTaskExecutor.setQueueCapacity(200);
		// 线程池维护线程的最少数量
		poolTaskExecutor.setCorePoolSize(30);
		// 线程池维护线程的最大数量
		poolTaskExecutor.setMaxPoolSize(200);
		// 线程池维护线程所允许的空闲时间
		poolTaskExecutor.setKeepAliveSeconds(10000);
		
		// rejection-policy：当pool已经达到max size的时候，如何处理新任务
		// CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
		poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

		poolTaskExecutor.initialize();

		return poolTaskExecutor;
	}

}
