package com.baeldung.async.config;

import java.util.concurrent.Executor;

import com.baeldung.async.CustomAsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@ComponentScan("com.baeldung.async")
public class SpringAsyncConfig implements AsyncConfigurer {

    /**
     * Defines a custom Executor used by @Async("threadPoolTaskExecutor") calls.
     */
    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("CustomPool-");
        executor.initialize();
        return executor;
    }

    /**
     * Defines the default Executor used by un-named @Async calls.
     */
    @Override
    public Executor getAsyncExecutor() {
        // You could return the named bean here, or create a new one.
        // Creating a new one for demonstration purposes.
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("DefaultAsync-");
        executor.initialize();
        return executor;
    }

    /**
     * Defines the exception handler for asynchronous method calls.
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
}
