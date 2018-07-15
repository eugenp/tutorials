package com.baeldung;

import com.baeldung.controller.MemoryStatsController;
import com.baeldung.data.MemoryStats;
import com.baeldung.service.EventsEmitter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for Events Emitter.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EventEmitterTests {

    @Autowired private EventsEmitter eventsEmitterService;

    @Test
    public void checkStatsEmitFrequencyIsOnceInOneSecond() throws InterruptedException {
        Flux<MemoryStats> statsFlux = eventsEmitterService.emitMemoryStats();
        Assert.assertTrue(statsFlux != null);
        List<MemoryStats> memoryStatsList = new ArrayList<>();

        statsFlux.subscribe(memoryStats -> memoryStatsList.add(memoryStats));

        // Wait for just above 2 seconds.
        Thread.sleep(2100);
        // It is expected to have at least 1 as one event is expected to be emitted in one second.
        Assert.assertTrue(memoryStatsList.size() > 1);
    }
}
