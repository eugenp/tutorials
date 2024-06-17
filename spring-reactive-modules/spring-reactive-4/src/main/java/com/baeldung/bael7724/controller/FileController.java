package com.baeldung.bael7724.controller;

import java.util.function.Function;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.bael7724.service.FileContentSearchService;
import com.baeldung.bael7724.service.FileService;
import com.baeldung.bael7724.util.ThreadLogger;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("bael7724/v1/files")
public class FileController {
    private final FileService fileService;
    private final FileContentSearchService fileContentSearchService;

    public FileController(FileService fileService, FileContentSearchService fileContentSearchService) {
        this.fileService = fileService;
        this.fileContentSearchService = fileContentSearchService;
    }

    @GetMapping(value = "/{name}")
    Mono<String> getFile(@PathVariable("name") String fileName) {
        return fileService.getFileContentAsString(fileName);
    }

    @GetMapping(value = "/{name}/blocking-search")
    Mono<Boolean> blockingSearch(@PathVariable("name") String fileName, @RequestParam String term) {
        return fileContentSearchService.blockingSearch(fileName, term);
    }

    @GetMapping(value = "/{name}/workable-blocking-search")
    Mono<Boolean> workableBlockingSearch(@PathVariable("name") String fileName, @RequestParam String term) {
        return fileContentSearchService.workableBlockingSearch(fileName, term)
            .doOnNext(aBoolean -> ThreadLogger.log("1. In Controller"))
            .map(Function.identity())
            .doOnNext(aBoolean -> ThreadLogger.log("2. In Controller"));
    }

    @GetMapping(value = "/{name}/blocking-search-on-custom-thread-pool")
    Mono<Boolean> blockingSearchOnCustomThreadPool(@PathVariable("name") String fileName, @RequestParam String term) {
        return fileContentSearchService.blockingSearchOnCustomThreadPool(fileName, term)
            .doOnNext(aBoolean -> ThreadLogger.log("1. In Controller"))
            .map(Function.identity())
            .doOnNext(aBoolean -> ThreadLogger.log("2. In Controller"));
    }

    @GetMapping(value = "/{name}/blocking-search-on-parallel-thread-pool")
    Mono<Boolean> blockingSearchOnParallelThreadPool(@PathVariable("name") String fileName, @RequestParam String term) {
        return fileContentSearchService.blockingSearchOnParallelThreadPool(fileName, term)
            .doOnNext(aBoolean -> ThreadLogger.log("1. In Controller"))
            .map(Function.identity())
            .doOnNext(aBoolean -> ThreadLogger.log("2. In Controller"));
    }

    @GetMapping(value = "/{name}/non-blocking-search")
    Mono<Boolean> nonBlockingSearch(@PathVariable("name") String fileName, @RequestParam String term) {
        return fileContentSearchService.nonBlockingSearch(fileName, term)
            .doOnNext(aBoolean -> ThreadLogger.log("1. In Controller"))
            .map(Function.identity())
            .doOnNext(aBoolean -> ThreadLogger.log("2. In Controller"));
    }
}
