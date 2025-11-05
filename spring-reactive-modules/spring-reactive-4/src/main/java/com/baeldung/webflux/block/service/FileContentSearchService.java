package com.baeldung.webflux.block.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.webflux.block.util.ThreadLogger;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class FileContentSearchService {

    @Autowired
    private FileService fileService;

    private final ExecutorService executorService;

    public FileContentSearchService() {
        ThreadFactory customThreadFactory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "custom-thread-" + threadNumber.getAndIncrement());
            }
        };

        executorService = Executors.newFixedThreadPool(10, customThreadFactory);
    }

    public Mono<Boolean> blockingSearch(String fileName, String searchTerm) {
        String fileContent = fileService.getFileContentAsString(fileName)
            .doOnNext(content -> ThreadLogger.log("1. BlockingSearch"))
            .block();

        boolean isSearchTermPresent = fileContent.contains(searchTerm);

        return Mono.just(isSearchTermPresent);
    }

    public Mono<Boolean> workableBlockingSearch(String fileName, String searchTerm) {
        return Mono.just("")
            .doOnNext(s -> ThreadLogger.log("1. WorkableBlockingSearch"))
            .publishOn(Schedulers.boundedElastic())
            .doOnNext(s -> ThreadLogger.log("2. WorkableBlockingSearch"))
            .map(s -> fileService.getFileContentAsString(fileName)
                .block()
                .contains(searchTerm))
            .doOnNext(s -> ThreadLogger.log("3. WorkableBlockingSearch"));
    }

    public Mono<Boolean> incorrectUseOfSchedulersSearch(String fileName, String searchTerm) {
        String fileContent = fileService.getFileContentAsString(fileName)
            .doOnNext(content -> ThreadLogger.log("1. IncorrectUseOfSchedulersSearch"))
            .publishOn(Schedulers.boundedElastic())
            .doOnNext(content -> ThreadLogger.log("2. IncorrectUseOfSchedulersSearch"))
            .block();

        boolean isSearchTermPresent = fileContent.contains(searchTerm);

        return Mono.just(isSearchTermPresent);
    }

    public Mono<Boolean> blockingSearchOnCustomThreadPool(String fileName, String searchTerm) {
        return Mono.just("")
            .doOnNext(s -> ThreadLogger.log("1. BlockingSearchOnCustomThreadPool"))
            .publishOn(Schedulers.fromExecutorService(executorService))
            .doOnNext(s -> ThreadLogger.log("2. BlockingSearchOnCustomThreadPool"))
            .map(s -> fileService.getFileContentAsString(fileName)
                .block()
                .contains(searchTerm))
            .doOnNext(s -> ThreadLogger.log("3. BlockingSearchOnCustomThreadPool"));
    }

    public Mono<Boolean> blockingSearchOnParallelThreadPool(String fileName, String searchTerm) {
        return Mono.just("")
            .doOnNext(s -> ThreadLogger.log("1. BlockingSearchOnParallelThreadPool"))
            .publishOn(Schedulers.parallel())
            .doOnNext(s -> ThreadLogger.log("2. BlockingSearchOnParallelThreadPool"))
            .map(s -> fileService.getFileContentAsString(fileName)
                .block()
                .contains(searchTerm))
            .doOnNext(s -> ThreadLogger.log("3. BlockingSearchOnParallelThreadPool"));
    }

    public Mono<Boolean> nonBlockingSearch(String fileName, String searchTerm) {
        return fileService.getFileContentAsString(fileName)
            .doOnNext(content -> ThreadLogger.log("1. NonBlockingSearch"))
            .map(content -> content.contains(searchTerm))
            .doOnNext(content -> ThreadLogger.log("2. NonBlockingSearch"));
    }
}
