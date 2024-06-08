package com.baeldung.bael7724.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.bael7724.service.FileContentSearchBlockingService;
import com.baeldung.bael7724.service.FileService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("bael7724/v1/files")
public class FileController {
    private final FileService fileService;

    private final FileContentSearchBlockingService fileContentSearchBlockingService;

    @Autowired
    public FileController(FileService fileService, FileContentSearchBlockingService fileContentSearchBlockingService) {
        this.fileService = fileService;
        this.fileContentSearchBlockingService = fileContentSearchBlockingService;
    }
    @GetMapping(value = "/{name}")
    Mono<String> getFile(@PathVariable("name") String fileName) {
        return fileService.getFileContentAsString(fileName);
    }

    @GetMapping(value = "/{name}/blocking-search")
    Mono<Boolean> getFileBlocking(@PathVariable("name") String fileName, @RequestParam String term) {
        return Mono.just(fileContentSearchBlockingService.search(fileName, term));
    }
}
