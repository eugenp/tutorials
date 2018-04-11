package com.baeldung.reactive;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.reactive.components.EventsConsumerService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = Spring5ReactiveApplication.class)
public class EventsConsumerServiceTest {
    
    @Test
    public void whenSubscribe_thenEventPresent() throws InterruptedException {
        List<Long> events = new ArrayList<>();
        EventsConsumerService.consume().subscribe(events::add);
        Thread.sleep(5000);
        Assert.assertTrue(events.size() > 0);
        Assert.assertTrue(events.stream().filter(a -> a == null).collect(Collectors.toList()).size() == 0);
    }
}
