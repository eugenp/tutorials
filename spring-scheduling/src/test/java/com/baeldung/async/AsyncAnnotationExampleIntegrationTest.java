package com.baeldung.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.baeldung.async.config.SpringAsyncConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit5ClassRunner.class)
@ContextConfiguration(classes = { SpringAsyncConfig.class }, loader = AnnotationConfigContextLoader.class)
public class AsyncAnnotationExampleIntegrationTest {

    @Autowired
    private AsyncComponent asyncAnnotationExample;

    // tests

    @Test
    public void testAsyncAnnotationForMethodsWithVoidReturnType() {
        asyncAnnotationExample.asyncMethodWithVoidReturnType();
    }

    @Test
    public void testAsyncAnnotationForMethodsWithReturnType() throws InterruptedException, ExecutionException {
 
        CompletableFuture<String> future = simpleAsyncService.asyncMethodWithReturnType();
        System.out.println("Invoking an asynchronous method. " + Thread.currentThread().getName());
    
        while (true) {
            if (future.isDone()) {
                System.out.println("Result from asynchronous process - " + future.get()); 
                break;
            }
        System.out.println("Continue doing something else. ");
        Thread.sleep(1000);
        }
    }

    @Test
    public void testAsyncAnnotationForMethodsWithConfiguredExecutor() {
        asyncAnnotationExample.asyncMethodWithConfiguredExecutor();
    }

    @Test
    public void testAsyncAnnotationForMethodsWithException() throws Exception {
        asyncAnnotationExample.asyncMethodWithExceptions();
    }
}
