package com.baeldung.bael7724.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class FileContentSearchService {
    private static final String MESSAGE_FORMAT = "%d. ThreadName: %s, Time: %s%n";
    private static final String ZONE_ID = "UTC";

    @Autowired
    private FileService fileService;

    public Mono<Boolean> blockingSearch(String fileName, String searchTerm) {
        String fileContent = fileService
            .getFileContentAsString(fileName)
            .doOnNext(content -> logThread(1))
            .block();

        boolean isSearchTermPresent = fileContent.contains(searchTerm);

        return Mono.just(isSearchTermPresent);
    }

    public Mono<Boolean> workableBlockingSearch(String fileName, String searchTerm) {
        String fileContent = fileService
            .getFileContentAsString(fileName)
            .doOnNext(content -> logThread(1))
            .subscribeOn(Schedulers.boundedElastic())
            .doOnNext(content -> logThread(2))
            .block();

        boolean isSearchTermPresent = fileContent.contains(searchTerm);

        return Mono.just(isSearchTermPresent);
    }

    public Mono<Boolean> nonBlockingSearch(String fileName, String searchTerm) {
        return fileService.getFileContentAsString(fileName)
            .doOnNext(content -> logThread(1))
            .map(content -> content.contains(searchTerm))
            .doOnNext(content -> logThread(2));
    }
    private void logThread(int i) {
        System.out.printf(MESSAGE_FORMAT, i, Thread.currentThread().getName(), OffsetDateTime.now(
            ZoneId.of(ZONE_ID)));
    }
}
