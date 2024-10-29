package com.baeldung.classloader;

import java.util.Objects;

import org.slf4j.LoggerFactory;

public class ForkedService implements Runnable {

    public static void main(String... args) {
        var log = LoggerFactory.getLogger(ForkedService.class);

        var handle = ProcessHandle.current();
        var parent = handle.parent();

        log.info("Starting service at pid {}. Parent: {}", handle.pid(), parent.isPresent() ? parent.get()
            .pid() : "(same VM)");

        var service = new ForkedService();
        try {
            service.run();
        } catch (IllegalStateException e) {
            System.exit(1);
        }
    }

    @Override
    public void run() {
        var log = LoggerFactory.getLogger(ForkedService.class);

        try {
            var cache = com.google.common.cache.CacheBuilder.newBuilder()
                .build();
            Objects.requireNonNull(cache);
            log.info("Should not have access to guava");

            throw new IllegalStateException();
        } catch (NoClassDefFoundError e) {
            log.info("Expected missing {} with narrow classpath", e.getMessage());
        }
    }
}