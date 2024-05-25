package com.baeldung.executerservicecustomthreadname;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ExecuterServiceCustomThreadName {

    public static void main(String[] args) {
        usingDefaultFactory();
        usingCommonsLang();
        usingCustomFactory();
        usingGuvava();
    }

    private static void usingDefaultFactory() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
        executorService.shutdown();
    }

    private static void usingCustomFactory() {
        CustomThreadFactory myThreadFactory = new CustomThreadFactory("MyCustomThread-");
        ExecutorService executorService = Executors.newFixedThreadPool(3, myThreadFactory);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
        executorService.shutdown();
    }

    private static void usingCommonsLang() {
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("MyCustomThread-%d").priority(Thread.MAX_PRIORITY).build();
        ExecutorService executorService = Executors.newFixedThreadPool(3, factory);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
        executorService.shutdown();
    }

    private static void usingGuvava() {
        System.out.println("USING Guvaa");
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("MyCustomThread-%d").build();
        ExecutorService executorService = Executors.newFixedThreadPool(3, namedThreadFactory);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
        executorService.shutdown();
    }
}
