package com.spaceprogram.bigcache;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.Serializable;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: treeder
 * Date: Nov 14, 2008
 * Time: 5:32:12 PM
 */
public class PerformanceTests {

     static S3Cache s3cache;

    @BeforeClass
    public static void setupCache() throws IOException {
        System.out.println("Setting up cache in S3CacheTests");
        Properties props = new Properties();
        InputStream is = S3CacheTests.class.getResourceAsStream("/aws-auth.properties");
        if (is == null) {
            throw new RuntimeException("No aws-auth.properties file found.");
        }
        props.load(is);
        ExecutorService executorService = Executors.newFixedThreadPool(25);
        s3cache = new S3Cache(props.getProperty("accessKey"), props.getProperty("secretKey"), props.getProperty("bucketName"), executorService);
    }

    @AfterClass
    public static void shutdown() {
        s3cache.getExecutorService().shutdown();
    }
    
    @Test
    public void testPuts() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 100; i++) {
            String id = "id-" + i;
            String s = i + " - bigcache is rad.";
            s3cache.put(id, s, 3600);
        }
        stopWatch.stop();
        System.out.println("duration = " + stopWatch.getTime());
    }

    @Test
    public void testPutsAsync() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String id = "id-" + i;
            String s = i + " - bigcache is rad.";
            Future f = s3cache.putAsync(id, s, 3600);
            futures.add(f);
        }
        // make sure all objects are done putting
        for (Future future : futures) {
            future.get();
        }
        stopWatch.stop();
        System.out.println("duration = " + stopWatch.getTime());
    }

    @Test
    public void testGets() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 100; i++) {
            String id = "id-" + i;
            Serializable serializable = s3cache.get(id);
        }
        stopWatch.stop();
        System.out.println("duration = " + stopWatch.getTime());
    }

     @Test
    public void testGetsAsync() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
         List<Future> futures = new ArrayList<Future>();
        for (int i = 0; i < 100; i++) {
            String id = "id-" + i;
            Future f = s3cache.getAsync(id);
            futures.add(f);
        }
          // make sure all objects are done getting
        for (Future future : futures) {
            Object o = future.get();
//            System.out.println("got: " + o);
        }
        stopWatch.stop();
        System.out.println("duration = " + stopWatch.getTime());
    }
}
