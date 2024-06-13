package com.baeldung.bael7724.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.bael7724.util.ThreadLogger;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class FileContentSearchService {
    @Autowired
    private FileService fileService;

    public Mono<Boolean> blockingSearch(String fileName, String searchTerm) {
        String fileContent = fileService
            .getFileContentAsString(fileName)
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

    public Mono<Boolean> nonBlockingSearch(String fileName, String searchTerm) {
        return fileService.getFileContentAsString(fileName)
            .doOnNext(content -> ThreadLogger.log("1. NonBlockingSearch"))
            .map(content -> content.contains(searchTerm))
            .doOnNext(content -> ThreadLogger.log("2. NonBlockingSearch"));
    }
}
