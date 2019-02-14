package com.wulizhou.ct.log.autoconfigue.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.wulizhou.ct.log.autoconfigue.aop.LogCenter;
import com.wulizhou.ct.log.autoconfigue.handle.LogDefaultAdapter;
import com.wulizhou.ct.log.autoconfigue.handle.LogHandle;

@Configuration
@ConditionalOnProperty(prefix = "ct.log", name="enabled", havingValue = "true", matchIfMissing=true)
@ConditionalOnMissingBean({LogCenter.class})
public class CtLogAutoConfig {

	@Bean
	@ConfigurationProperties(prefix = "ct.log")
	public LogConfig logConfig() {
		return new LogConfig();
	}
	
	@Bean
	public LogCenter logCenter(LogConfig config, LogHandle logHandle, Executor executor) {
		LogCenter center = new LogCenter();
		center.setLogConfig(config);
		center.setLogHandle(logHandle);
		center.setExecutor(executor);
		return center;
	}
	
	@Bean
	@ConditionalOnMissingBean({LogHandle.class})
	public LogHandle logHandle() {
		return new LogDefaultAdapter();
	}
	
	@Bean
	public Executor springServiceExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 配置核心线程数
		executor.setCorePoolSize(4);
		// 配置最大线程数
		executor.setMaxPoolSize(16);
		// 配置队列大小
		executor.setQueueCapacity(1000);
		// 配置线程池中的线程的名称前缀
		executor.setThreadNamePrefix("spring-");
		// 当pool已经达到max size的时候，不在新线程中执行任务，而是由调用者所在的线程来执行
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 执行初始化
		executor.initialize();
		return executor;
	}
	
}
