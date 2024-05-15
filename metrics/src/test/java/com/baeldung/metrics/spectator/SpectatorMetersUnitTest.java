package com.baeldung.metrics.spectator;

import com.netflix.spectator.api.*;
import com.netflix.spectator.api.patterns.LongTaskTimer;
import com.netflix.spectator.api.patterns.PolledMeter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpectatorMetersUnitTest {
    @Test
    public void spectatorCounterTest(){

        class MyListProcessor {
            private final Counter insertCounter;
            private final Counter removeCounter;
            List<String> requestList = new ArrayList();

            private MyListProcessor(Registry registry){
                insertCounter = registry.counter("list.insert.count");
                removeCounter = registry.counter("list.remove.count");
            }

            private void addToList(String element){
                requestList.add(element);
                insertCounter.increment();
            }

            private void removeFromList(){
                requestList.remove(0);
                removeCounter.increment();
            }
        }

        MyListProcessor myListProcessor = new MyListProcessor(new DefaultRegistry());
        myListProcessor.addToList("element1");
        myListProcessor.addToList("element2");
        myListProcessor.addToList("element3");
        myListProcessor.removeFromList();

        assertEquals(3, myListProcessor.insertCounter.count());
        assertEquals(1, myListProcessor.removeCounter.count());

    }

    @Test
    public void spectatorTimerTest() throws Exception {

        class MyRequestProcessor {
            private final Timer requestLatency;

            private MyRequestProcessor(Registry registry) {
                requestLatency = registry.timer("app.request.latency");
            }

            private String processRequest(int input) throws Exception {
                return requestLatency.record(() -> handleRequest(input));

            }

            private String handleRequest(int input) throws InterruptedException {
                try {
                    Thread.sleep(input);
                    return "Done";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }

        MyRequestProcessor myRequestProcessor = new MyRequestProcessor(new DefaultRegistry());
        myRequestProcessor.processRequest(3000);
        assertEquals(1, myRequestProcessor.requestLatency.count());
        assertThat(myRequestProcessor.requestLatency.totalTime()).isBetween(3000000000L, 4000000000L);
    }

    @Test
    public void spectatorLongTaskTimerTest() throws Exception {

        class MyRequestProcessor {
            private final LongTaskTimer refreshDuration;
            private long duration;

            private MyRequestProcessor(Registry registry) {
                refreshDuration = LongTaskTimer.get(registry, registry.createId("metadata.refreshDuration"));
            }

            private String processRequest(int input) throws Exception {
                final long taskId = refreshDuration.start();
                try {
                    Thread.sleep(input);
                    return "Done";

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw e;
                } finally {
                    refreshDuration.stop(taskId);
                }

            }
        }

        MyRequestProcessor myRequestProcessor = new MyRequestProcessor(new DefaultRegistry());
        myRequestProcessor.processRequest(3000);
        System.out.println(myRequestProcessor.refreshDuration.measure());
        System.out.println(myRequestProcessor.duration);
        System.out.println(myRequestProcessor.refreshDuration.duration());
    }

    @Test
    public void spectatorGauges_polledMeter_Test(){
        class MyList {

            private List<String> list = new ArrayList();;
            private AtomicInteger listSize = new AtomicInteger(0);

            private MyList(Registry registry) {
                PolledMeter.using(registry)
                  .withName("list.size")
                  .monitorValue(listSize);
            }

            private void addToList(String element){
                list.add(element);
                listSize.incrementAndGet();
            }
            private void removeFromList(){
                list.remove(0);
                listSize.decrementAndGet();
            }
            private int size(){
                return list.size();
            }
        }

        MyList myList = new MyList(new DefaultRegistry());
        myList.addToList("element1");
        myList.addToList("element2");
        myList.addToList("element3");
        myList.addToList("element4");
        myList.removeFromList();
        assertEquals(myList.size(), myList.listSize.get());
    }

    @Test
    public void spectatorGauges_ActiveGauges_Test(){
        class MyList {

            private List<String> list = new ArrayList();;
            private AtomicInteger listSize = new AtomicInteger(0);
            private Gauge gauge;

            private MyList(Registry registry) {
                gauge = registry.gauge("list.size");
            }

            private void addToList(String element){
                list.add(element);
                listSize.incrementAndGet();
                gauge.set(listSize.get());
            }
            private void removeFromList(){
                list.remove(0);
                listSize.decrementAndGet();
                gauge.set(listSize.get());
            }
            private int size(){
                return list.size();
            }
        }

        MyList myList = new MyList(new DefaultRegistry());
        myList.addToList("element1");
        myList.addToList("element2");
        myList.addToList("element3");
        myList.addToList("element4");
        myList.removeFromList();
        assertEquals(3.0, myList.gauge.value());
    }

    @Test
    public void spectatorDistributionSummaryTest() throws Exception {

        class MyRequestProcessor {
            private final DistributionSummary distributionSummary;

            private MyRequestProcessor(Registry registry) {
                distributionSummary = registry.distributionSummary("app.request.size");
            }

            private void processRequest(String input) throws Exception {
                distributionSummary.record((long) input.length());
                handleRequest();
            }

            private void handleRequest() throws InterruptedException {
                try {
                    Thread.sleep(3000);
                    return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }

        MyRequestProcessor myRequestProcessor = new MyRequestProcessor(new DefaultRegistry());
        myRequestProcessor.processRequest("This is my sample input.");
        assertEquals(1, myRequestProcessor.distributionSummary.count());
        assertEquals("This is my sample input.".length(), (int) myRequestProcessor.distributionSummary.totalAmount());
    }

}

