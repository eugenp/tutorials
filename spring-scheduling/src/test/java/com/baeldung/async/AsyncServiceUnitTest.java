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

    @Test
    public void testAsyncAnnotationForMergedServicesResponse() throws InterruptedException, ExecutionException {
         
        //   we use the injected bean 'asyncServiceExample'.
        CompletableFuture<String> completableFuture = asyncServiceExample.asyncMergeServicesResponse();

        System.out.println("Invoking asynchronous methods. " + Thread.currentThread().getName());

        // This loop simulates the calling thread continuing its work while waiting for the async result.
        while (true) {
            if (completableFuture.isDone()) {
                System.out.println("Result from asynchronous process - " + completableFuture.get());
                break;
            }
            System.out.println("Continue doing something else. ");
            Thread.sleep(1000);
        }
    }
}
