package com.baeldung.smartbatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author KPentaris
 * @date 07/06/2023
 * @project design-patterns-behavioral-2
 */
public class BatchingApp {

    static void simpleProcessing() throws Exception {
        final Path testPath = Paths.get("./testio.txt");
        testPath.toFile().createNewFile();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(100);
        Set<Future> futures = new HashSet<>();
        for (int i = 0; i < 50000; i++) {
            futures.add(executorService.submit(() -> {
                try {
                    Files.write(testPath, Collections.singleton(Thread.currentThread().getName()), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        }
        long start = System.currentTimeMillis();
        for (Future future : futures) {
            future.get();
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start));
        executorService.shutdown();
    }

    static void batchedProcessing() throws Exception {
        final Path testPath = Paths.get("./testio.txt");
        testPath.toFile().createNewFile();
        SmartBatcher batcher = new SmartBatcher(10, strings -> {
            List<String> content = new ArrayList<>(strings);
            content.add("-----Batch Operation-----");
            try {
                Files.write(testPath, content, StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        for (int i = 0; i < 50000; i++) {
            batcher.submit(Thread.currentThread().getName() + "-1");
        }
        long start = System.currentTimeMillis();
        while (!batcher.finished()) {
            Thread.sleep(10);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) throws Exception {
//        simpleProcessing();
        batchedProcessing();
    }
}
