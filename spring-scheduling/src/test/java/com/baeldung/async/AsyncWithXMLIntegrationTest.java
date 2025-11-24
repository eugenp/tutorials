package com.baeldung.async;

// JUnit 5 Imports
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith;

// Spring Imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension; // Use JUnit 5 Extension
import com.baeldung.async.AsyncComponent;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:springAsync-config.xml")
public class AsyncWithXMLIntegrationTest {

    @Autowired
    private AsyncComponent asyncAnnotationExample;

    @Test
    public void testAsyncAnnotationForMethodsWithVoidReturnType() throws InterruptedException {
        // Invoking the async method. The calling thread will immediately proceed.
        asyncAnnotationExample.asyncMethodWithVoidReturnType();
        
        // Sleep is required in void methods to ensure the asynchronous thread 
        // has time to execute before the test completes and the application context shuts down.
        Thread.sleep(2000);
    }
}
