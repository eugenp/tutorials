package com.baeldung.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;

// JUnit 5 Imports
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith;

// Spring Imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

// Note: Assuming AsyncComponent and SimpleAsyncService are mock interfaces 
// or actual service implementations used in your application context.
// The configuration class (SpringAsyncConfig) needs to be imported here.
import com.baeldung.async.config.SpringAsyncConfig;
import com.baeldung.async.SimpleAsyncService; // Added import based on usage in test

// Replace @RunWith(SpringJUnit4ClassRunner.class) with @ExtendWith(SpringExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringAsyncConfig.class }, loader = AnnotationConfigContextLoader.class)
public class AsyncAnnotationExampleIntegrationTest {

    // Assuming AsyncComponent is the bean for void methods
    @Autowired
    private AsyncComponent asyncAnnotationExample; 
    
    // Assuming SimpleAsyncService is the bean for CompletableFuture methods
    // NOTE: If both methods are on one service, you should only inject one bean.
    @Autowired 
    private SimpleAsyncService simpleAsyncService;

    @Test
    public void testAsyncAnnotationForMethodsWithVoidReturnType() {
        asyncAnnotationExample.asyncMethodWithVoidReturnType();
        // Since this is void, we typically assert that the calling thread continues immediately,
        // but for a simple integration test, invoking it is sufficient.
    }

    @Test
    public void testAsyncAnnotationForMethodsWithReturnType() throws InterruptedException, ExecutionException {
 
        // Using the SimpleAsyncService bean for the CompletableFuture test, as per the original snippet
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
        // This test relies on an exception handler (AsyncUncaughtExceptionHandler) 
        // being configured for void return types.
        asyncAnnotationExample.asyncMethodWithExceptions();
    }
}
