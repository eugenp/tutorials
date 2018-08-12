package com.baeldung.rxjava.convertor;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import io.reactivex.schedulers.Schedulers;

public class ReactiveFileReaderUnitTest {

    private List<String> testList = Arrays.asList("test1", "test2", "test3", "test4");

    private ReactiveFileReader reader = new ReactiveFileReader("test.txt");

    @Test
    public void whenReadFileInSynchronous_thenAllLinesAreReceived() {
        Flowable<String> flowable = reader.readFileInSync();

        TestSubscriber<String> testSubscriber = flowable.observeOn(Schedulers.computation())
            .test();

        testSubscriber.awaitTerminalEvent();

        List<String> receivedStrings = testSubscriber.getEvents()
            .get(0)
            .stream()
            .map(v -> v.toString())
            .collect(Collectors.toList());

        assertEquals(testList, receivedStrings);
    }

    @Test
    public void whenReadFileInAsynchronous_thenAllLinesAreReceived() {
        Flowable<String> flowable = reader.readFileInAsync();

        TestSubscriber<String> testSubscriber = flowable.observeOn(Schedulers.computation())
            .test();

        testSubscriber.awaitTerminalEvent();

        String receivedString = testSubscriber.getEvents()
            .get(0)
            .stream()
            .map(v -> v.toString())
            .reduce("", String::concat);

        List<String> receivedStringList = Arrays.asList(receivedString.split("\\r?\\n"));

        assertEquals(testList, receivedStringList);
    }
}
