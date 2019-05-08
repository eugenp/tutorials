package com.baeldung.concurrent.skiplist;

import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConcurrentSkipListSetIntegrationTest {

    @Test
    public void givenThreadsProducingEvents_whenGetForEventsFromLastMinute_thenReturnThoseEventsInTheLockFreeWay() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        EventWindowSort eventWindowSort = new EventWindowSort();
        int numberOfThreads = 2;
        //when
        Runnable producer = () -> IntStream
          .rangeClosed(0, 100)
          .forEach(index -> eventWindowSort.acceptEvent(new Event(ZonedDateTime
            .now()
            .minusSeconds(index), UUID
            .randomUUID()
            .toString())));

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(producer);
        }

        Thread.sleep(500);

        ConcurrentNavigableMap<ZonedDateTime, String> eventsFromLastMinute = eventWindowSort.getEventsFromLastMinute();

        long eventsOlderThanOneMinute = eventsFromLastMinute
          .entrySet()
          .stream()
          .filter(e -> e
            .getKey()
            .isBefore(ZonedDateTime
              .now()
              .minusMinutes(1)))
          .count();
        assertEquals(eventsOlderThanOneMinute, 0);

        long eventYoungerThanOneMinute = eventsFromLastMinute
          .entrySet()
          .stream()
          .filter(e -> e
            .getKey()
            .isAfter(ZonedDateTime
              .now()
              .minusMinutes(1)))
          .count();

        //then
        assertTrue(eventYoungerThanOneMinute > 0);

        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    @Test
    public void givenThreadsProducingEvents_whenGetForEventsOlderThanOneMinute_thenReturnThoseEventsInTheLockFreeWay() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        EventWindowSort eventWindowSort = new EventWindowSort();
        int numberOfThreads = 2;
        //when
        Runnable producer = () -> IntStream
          .rangeClosed(0, 100)
          .forEach(index -> eventWindowSort.acceptEvent(new Event(ZonedDateTime
            .now()
            .minusSeconds(index), UUID
            .randomUUID()
            .toString())));

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(producer);
        }

        Thread.sleep(500);

        ConcurrentNavigableMap<ZonedDateTime, String> eventsFromLastMinute = eventWindowSort.getEventsOlderThatOneMinute();

        long eventsOlderThanOneMinute = eventsFromLastMinute
          .entrySet()
          .stream()
          .filter(e -> e
            .getKey()
            .isBefore(ZonedDateTime
              .now()
              .minusMinutes(1)))
          .count();
        assertTrue(eventsOlderThanOneMinute > 0);

        long eventYoungerThanOneMinute = eventsFromLastMinute
          .entrySet()
          .stream()
          .filter(e -> e
            .getKey()
            .isAfter(ZonedDateTime
              .now()
              .minusMinutes(1)))
          .count();

        //then
        assertEquals(eventYoungerThanOneMinute, 0);

        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

}