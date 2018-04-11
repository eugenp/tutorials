package com.baeldung.reactive;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.reactive.components.EventsEmitterService;

public class EventsEmitterServiceTest {
    
    @Test
    public void givenEventsEmitterService_whenSubscribe_thenEventsPresent() throws InterruptedException
    {
        List<Long> events = new ArrayList<>();
        EventsEmitterService.getInstance().subscribe(events::add);
        Thread.sleep(5000);
        Assert.assertTrue(events.size() > 0);
        Assert.assertTrue(events.stream().filter(a -> a == null).collect(Collectors.toList()).size() == 0);
    }
    
}
