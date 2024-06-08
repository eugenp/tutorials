package com.baeldung.bael7724.service;

import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.scheduler.Schedulers;

@Service
public class FileContentSearchBlockingService {

    @Autowired
    private FileService fileService;

    public boolean search(String fileName, String searchTerm) {
        return fileService
            .getFileContentAsString(fileName)
            .publishOn(Schedulers.fromExecutor(Executors.newFixedThreadPool(2))) // Use your custom thread pool
            .block().contains(searchTerm);
    }
}
