package com.insightsource.cache.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.insightsource.cache.KittyCache;

public class PerformanceTests {
    private CacheManager cacheManager;
    public Cache ehcache;
    private KittyCache kittycache;
    private EhcacheWrapper ehcacheWrapper;

    @BeforeClass
    public static void setupCacheManager() {
    }

    @Before
    public void setupCache() {
        cacheManager = new CacheManager();
        ehcache = cacheManager.getCache("sampleCache2");
        ehcacheWrapper = new EhcacheWrapper(ehcache);
        kittycache = new KittyCache(10000);
    }

    @After
    public void clear() {
        cacheManager.shutdown();
    }

    @AfterClass
    public static void shutdown() {
    }

    @Test
    public void test10000() {
        System.out.println("Test 10,000 Puts");
        doPut(10000);
    }

    @Test
    public void test100000() {
        System.out.println("Test 100,000 Puts");
        doPut(100000);
    }

    @Test
    public void test1000000() {
        System.out.println("Test 1,000,000 Puts");
        doPut(1000000);
    }

    @Test
    public void testConcurrent() throws ExecutionException, InterruptedException {
        System.out.println("Test 100 Thread Concurrency and 1,000,000 Puts");
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        List<Future<Long>> futures = new ArrayList<Future<Long>>();
        for (int i = 0; i < 1000; i++) {
            Future<Long> futureDuration = executorService.submit(new CacheHammer(ehcacheWrapper));
            futures.add(futureDuration);
        }
        long totalDuration = 0;
        for (Future<Long> future : futures) {
            totalDuration += future.get();
        }
        System.out.println("ehcache=" + totalDuration);

        futures = new ArrayList<Future<Long>>();
        for (int i = 0; i < 1000; i++) {
            Future<Long> futureDuration = executorService.submit(new CacheHammer(kittycache));
            futures.add(futureDuration);
        }
        totalDuration = 0;
        for (Future<Long> future : futures) {
            totalDuration += future.get();
        }
        System.out.println("kittycache=" + totalDuration);

    }

    private void doPut(int times) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            ehcache.put(new Element("key" + i, "value" + i));
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("ehcache=" + duration);

        start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            kittycache.put("key" + i, "value" + i, 5000);
        }
        duration = System.currentTimeMillis() - start;
        System.out.println("kitty=" + duration);
    }


}
