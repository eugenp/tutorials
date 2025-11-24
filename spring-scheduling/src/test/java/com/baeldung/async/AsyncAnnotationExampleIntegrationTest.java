package com.baeldung.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;
import com.baeldung.async.AsyncComponent;
// JUnit 5 Imports
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith;

// Spring Imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import com.baeldung.async.config.SpringAsyncConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringAsyncConfig.class }, loader = AnnotationConfigContextLoader.class)
public class AsyncAnnotationExampleIntegrationTest {

    @Autowired
    private AsyncComponent asyncAnnotationExample; 

    @Test
    public void testAsyncAnnotationForMethodsWithVoidReturnType() {
        asyncAnnotationExample.asyncMethodWithVoidReturnType();
    }

    @Test
    public void testAsyncAnnotationForMethodsWithReturnType() throws InterruptedException, ExecutionException {
 
        CompletableFuture<String> future = asyncAnnotationExample.asyncMethodWithReturnType();
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
