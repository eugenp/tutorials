package com.baeldung.java.concurrentmap;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConcurrentMapPerformanceManualTest {

    @Test
    public void givenMaps_whenGetPut500KTimes_thenConcurrentMapFaster() throws Exception {
        Map<String, Object> hashtable = new Hashtable<>();
        Map<String, Object> synchronizedHashMap = Collections.synchronizedMap(new HashMap<>());
        Map<String, Object> concurrentHashMap = new ConcurrentHashMap<>();

        long hashtableAvgRuntime = timeElapseForGetPut(hashtable);
        long syncHashMapAvgRuntime = timeElapseForGetPut(synchronizedHashMap);
        long concurrentHashMapAvgRuntime = timeElapseForGetPut(concurrentHashMap);

        System.out.println(String.format("Hashtable: %s, syncHashMap: %s, ConcurrentHashMap: %s", hashtableAvgRuntime, syncHashMapAvgRuntime, concurrentHashMapAvgRuntime));

        assertTrue(hashtableAvgRuntime > concurrentHashMapAvgRuntime);
        assertTrue(syncHashMapAvgRuntime > concurrentHashMapAvgRuntime);

    }

    private long timeElapseForGetPut(Map<String, Object> map) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        long startTime = System.nanoTime();
        for (int i = 0; i < 4; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 500_000; j++) {
                    int value = ThreadLocalRandom.current().nextInt(10000);
                    String key = String.valueOf(value);
                    map.put(key, value);
                    map.get(key);
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        return (System.nanoTime() - startTime) / 500_000;
    }

    @Test
    public void givenConcurrentMap_whenKeyWithSameHashCode_thenPerformanceDegrades() throws InterruptedException {
        class SameHash {
            @Override
            public int hashCode() {
                return 1;
            }
        }
        int executeTimes = 5000;

        Map<SameHash, Integer> mapOfSameHash = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        long sameHashStartTime = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < executeTimes; j++) {
                    mapOfSameHash.put(new SameHash(), 1);
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        long mapOfSameHashDuration = System.currentTimeMillis() - sameHashStartTime;
        Map<Object, Integer> mapOfDefaultHash = new ConcurrentHashMap<>();
        executorService = Executors.newFixedThreadPool(2);
        long defaultHashStartTime = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < executeTimes; j++) {
                    mapOfDefaultHash.put(new Object(), 1);
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        long mapOfDefaultHashDuration = System.currentTimeMillis() - defaultHashStartTime;
        assertEquals(executeTimes * 2, mapOfDefaultHash.size());
        assertEquals(executeTimes * 2, mapOfSameHash.size());
        System.out.println(String.format("same-hash: %s, default-hash: %s", mapOfSameHashDuration, mapOfDefaultHashDuration));
        assertTrue("same hashCode() should greatly degrade performance", mapOfSameHashDuration > mapOfDefaultHashDuration * 10);
    }

}
