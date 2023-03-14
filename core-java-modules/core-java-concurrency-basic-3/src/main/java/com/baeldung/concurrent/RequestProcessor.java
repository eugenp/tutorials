package com.baeldung.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RequestProcessor {

    private Map<String, String> requestStatuses = new HashMap<>();

    public String processRequest() {
        String requestId = UUID.randomUUID().toString();
        requestStatuses.put(requestId, "PROCESSING");

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule((() -> {
            requestStatuses.put(requestId, "DONE");
        }), getRandomNumberBetween(500, 2000), TimeUnit.MILLISECONDS);

        return requestId;
    }

    public String getStatus(String requestId) {
        return requestStatuses.get(requestId);
    }

    private int getRandomNumberBetween(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
