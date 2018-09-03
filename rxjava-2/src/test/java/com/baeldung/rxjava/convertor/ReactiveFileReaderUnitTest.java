package com.baeldung.rxjava.convertor;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
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
    public void givenSyncFileReader_whenConvertByGenerate_thenAllLinesAreReceived() {
        Flowable<String> flowable = reader.readFileConvertSyncToObservablesByGenerate();

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
    public void givenSyncFileReader_whenConvertFromIterable_thenAllLinesAreReceived() {
        Flowable<String> flowable = reader.readFileConvertSyncToObservablesFromIterable();

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
    public void givenAsyncFileReader_whenConvertByCreate_thenAllLinesAreReceived() {
        Flowable<String> flowable = reader.readFileConvertAsyncToObservablesByCreate();

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
        
        reader.cleanBuffer();

    }

    @Test
    public void givenAsyncFileReader_whenConvertFromFuture_thenAllLinesAreReceived() {

        Flowable<Integer> flowable = null;
        try {
            flowable = reader.readFileConvertAsyncToObservablesFromFuture();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TestSubscriber<Integer> testSubscriber = flowable.observeOn(Schedulers.computation())
            .test();

        testSubscriber.awaitTerminalEvent();

        String receivedString = new String(reader.getBuffer()
            .array()).trim();

        reader.cleanBuffer();

        List<String> receivedStringList = Arrays.asList(receivedString.split("\\r?\\n"));

        assertEquals(testList, receivedStringList);
    }

}
