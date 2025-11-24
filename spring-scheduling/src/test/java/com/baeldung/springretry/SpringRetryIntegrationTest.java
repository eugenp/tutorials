package com.baeldung.springretry;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
  
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ContextConfiguration;
 
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.baeldung.springretry.logging.LogAppender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class SpringRetryIntegrationTest {

    @Autowired
    private MyService myService;
    @Value("${retry.maxAttempts}")
    private String maxAttempts;

    @Autowired
    private RetryTemplate retryTemplate;
    
    @Autowired
    private RetryTemplate retryTemplateNoRetry; // Corrected variable name from AppConfig

    @Test(expected = RuntimeException.class)
    public void givenRetryService_whenCallWithException_thenRetry() {
        myService.retryService();
    }

    @Test
    public void givenRetryService_whenCallWithException_thenPrintsRetryCount() {
        assertThrows(RuntimeException.class, () -> {
            myService.retryService();
        });

        List<String> retryCountLogs = LogAppender.getLogMessages()
          .stream()
          .filter(message -> message.contains("Retry Number:"))
          .collect(Collectors.toList());

        // Assuming default max attempts (3) means retry count goes up to 2 (0, 1, 2)
        assertEquals("Retry Number: 2", retryCountLogs.get(retryCountLogs.size() - 1));
    }

    @Test
    public void givenRetryServiceWithRecovery_whenCallWithException_thenRetryRecover() throws SQLException {
        myService.retryServiceWithRecovery(null);
    }

    @Test
    public void givenRetryServiceWithCustomization_whenCallWithException_thenRetryRecover() throws SQLException {
        myService.retryServiceWithCustomization(null);
        // Ensure myService is called exactly maxAttempts times
        verify(myService, times(2)).retryServiceWithCustomization(any());
    }

    @Test
    public void givenRetryServiceWithExternalConfiguration_whenCallWithException_thenRetryRecover() throws SQLException {
        // Note: maxAttempts here is currently a String "2" from properties, but verify() needs an int
        // We assume maxAttempts is 2 here for successful verification
        myService.retryServiceWithExternalConfiguration(null);
        verify(myService, times(Integer.parseInt(maxAttempts))).retryServiceWithExternalConfiguration(any());
    }

    @Test(expected = RuntimeException.class)
    public void givenTemplateRetryService_whenCallWithException_thenRetry() {
        retryTemplate.execute(arg0 -> {
            myService.templateRetryService();
            return null;
        });
    }


    @Test(expected = RuntimeException.class)
    public void givenTemplateRetryServiceWithZeroAttempts_whenCallWithException_thenFailImmediately() {
        retryTemplateNoRetry.execute(arg0 -> {
            myService.templateRetryService();
            return null;
        });
        verify(myService, times(1)).templateRetryService(); 
    }

    // ------------------------------------------------------------------
    // NEW TEST FOR @ConcurrencyLimit
    // ------------------------------------------------------------------
    @Test
    public void givenConcurrentLimitService_whenCalledByManyThreads_thenLimitIsEnforced() throws InterruptedException {
        int limit = 5;
        int totalThreads = 10;
        // Latch to hold all threads until we're ready to start
        CountDownLatch startLatch = new CountDownLatch(1);
        // Latch to wait for all threads to finish
        CountDownLatch finishLatch = new CountDownLatch(totalThreads);
        // Counter for the number of threads that started execution (should equal the limit)
        AtomicInteger activeThreads = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);

        for (int i = 0; i < totalThreads; i++) {
            executor.submit(() -> {
                try {
                    // Wait until all threads are created and ready
                    startLatch.await();
                    
                    // Call the method with the concurrency limit
                    activeThreads.incrementAndGet(); // Increment before method call 
                    myService.concurrentLimitService();
                    activeThreads.decrementAndGet(); // Decrement after method call
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        // 1. Release all threads simultaneously
        startLatch.countDown();

        // Give the initial threads time to enter the method and block the rest (Service sleeps for 1000ms)
        Thread.sleep(200); 

        // 2. Assert that only 'limit' number of threads are active
        assertEquals(limit, activeThreads.get());

        // 3. Wait for all threads to finish execution (up to 2 seconds)
        finishLatch.await(2, TimeUnit.SECONDS); 
        
        // 4. Final verification that all threads completed
        assertEquals(0, activeThreads.get());
        
        executor.shutdownNow();
    }
}
