package com.baeldung.executerservicecustomthreadname;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuterServiceCustomThreadName {

    private static final Logger logger = LoggerFactory.getLogger(ExecuterServiceCustomThreadName.class);

    public static void main(String[] args) {
        usingDefaultFactory();
        usingCommonsLang();
        usingCustomFactory();
        usingGuvava();
    }

    private static void usingDefaultFactory() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> logger.info(Thread.currentThread().getName()));
        }
        executorService.shutdown();
    }

    private static void usingCustomFactory() {
        CustomThreadFactory myThreadFactory = new CustomThreadFactory("MyCustomThread-");
        ExecutorService executorService = Executors.newFixedThreadPool(3, myThreadFactory);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> logger.info(Thread.currentThread().getName()));
        }
        executorService.shutdown();
    }

    private static void usingCommonsLang() {
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("MyCustomThread-%d").priority(Thread.MAX_PRIORITY).build();
        ExecutorService executorService = Executors.newFixedThreadPool(3, factory);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> logger.info(Thread.currentThread().getName()));
        }
        executorService.shutdown();
    }

    private static void usingGuvava() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("MyCustomThread-%d").build();
        ExecutorService executorService = Executors.newFixedThreadPool(3, namedThreadFactory);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> logger.info(Thread.currentThread().getName()));
        }
        executorService.shutdown();
    }
}
