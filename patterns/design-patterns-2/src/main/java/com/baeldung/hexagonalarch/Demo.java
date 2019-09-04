package com.baeldung.hexagonalarch;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.baeldung.hexagonalarch.adapter.FileSystemStorageService;
import com.baeldung.hexagonalarch.adapter.RandomFactRepository;
import com.baeldung.hexagonalarch.adapter.StandardOutputService;
import com.baeldung.hexagonalarch.domain.RandomFactsController;
import com.baeldung.hexagonalarch.domain.ports.FetchFactsRepository;
import com.baeldung.hexagonalarch.domain.ports.StorageService;

public class Demo {

    public static void main(String[] args) {
        FetchFactsRepository fetchFactsRepository = new RandomFactRepository();
        StorageService fileSystemStorageService = new FileSystemStorageService();
        StorageService standardOutputService = new StandardOutputService();

        //GiveMeIdeasService giveMeIdeasService = new GiveMeIdeasService(fetchIdeasRepository, fileSystemStorageService);
        RandomFactsController randomFactsController = new RandomFactsController(fetchFactsRepository, standardOutputService);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(randomFactsController::execute, 0, 2, TimeUnit.SECONDS);
    }

}
