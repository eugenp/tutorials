package com.baeldung.seda.springintegration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskExecutorConfiguration {

    @Bean("receiveTextChannelThreadPool")
    public TaskExecutor receiveTextChannelThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("receive-text-channel-thread-pool");
        executor.initialize();
        return executor;
    }

    @Bean("splitWordsChannelThreadPool")
    public TaskExecutor splitWordsChannelThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("split-words-channel-thread-pool");
        executor.initialize();
        return executor;
    }

    @Bean("toLowerCaseChannelThreadPool")
    public TaskExecutor toLowerCaseChannelThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("tto-lower-case-channel-thread-pool");
        executor.initialize();
        return executor;
    }

    @Bean("countWordsChannelThreadPool")
    public TaskExecutor countWordsChannelThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("count-words-channel-thread-pool");
        executor.initialize();
        return executor;
    }

    @Bean("returnResponseChannelThreadPool")
    public TaskExecutor returnResponseChannelThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("return-response-channel-thread-pool");
        executor.initialize();
        return executor;
    }

}
