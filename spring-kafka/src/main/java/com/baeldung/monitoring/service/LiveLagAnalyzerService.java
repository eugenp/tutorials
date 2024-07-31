package com.baeldung.monitoring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class LiveLagAnalyzerService {

    private final LagAnalyzerService lagAnalyzerService;
    private final String groupId;

    @Autowired
    public LiveLagAnalyzerService(
      LagAnalyzerService lagAnalyzerService,
      @Value(value = "${monitor.kafka.consumer.groupid}") String groupId) {
        this.lagAnalyzerService = lagAnalyzerService;
        this.groupId = groupId;
    }

    @Scheduled(fixedDelay = 5000L)
    public void liveLagAnalysis() throws ExecutionException, InterruptedException {
        lagAnalyzerService.analyzeLag(groupId);
    }
}
