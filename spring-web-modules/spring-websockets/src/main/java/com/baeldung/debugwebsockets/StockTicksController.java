package com.baeldung.debugwebsockets;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class StockTicksController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public StockTicksController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Scheduled(fixedRate = 3000)
    public void sendTicks() {
        simpMessagingTemplate.convertAndSend("/topic/ticks", getStockTicks());
    }

    private Map<String, Integer> getStockTicks() {
        Map<String, Integer> ticks = new HashMap<>();
        ticks.put("AAPL", getRandomTick());
        ticks.put("GOOGL", getRandomTick());
        ticks.put("MSFT", getRandomTick());
        ticks.put("TSLA", getRandomTick());
        ticks.put("AMZN", getRandomTick());
        ticks.put("HPE", getRandomTick());

        return ticks;
    }

    private int getRandomTick() {
        return ThreadLocalRandom.current().nextInt(-100, 100 + 1);
    }
}