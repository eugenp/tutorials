package com.baeldung.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.async.config.SpringAsyncConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringAsyncConfig.class }, loader = AnnotationConfigContextLoader.class)
public class AsyncServiceUnitTest {

    @Autowired
    private AsyncService asyncServiceExample;

    // tests

    @Test
    public void testAsyncAnnotationForMergedServicesResponse() throws InterruptedException, ExecutionException {
        CompletableFuture<String> completableFuture = asyncServiceExample.asyncMergeServicesResponse();

        while (true) {
            if (completableFuture.isDone()) {
                break;
            }
            Thread.sleep(1000);
        }
    }

}
