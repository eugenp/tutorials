package com.baeldung.springretry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.baeldung.springretry.logging.LogAppender;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AppConfig.class)
public class SpringRetryIntegrationTest {

    @MockitoSpyBean
    private MyService myService;
    
    // Default maxAttempts is 3 for @Retryable (1 initial call + 2 retries)
    @Value("${retry.maxAttempts}")
    private String maxAttempts;

    @Autowired
    private RetryTemplate retryTemplate;
    
    @Autowired
    private RetryTemplate retryTemplateNoRetry;

    // --- @Retryable Tests ---
    
    @Test
    public void givenRetryService_whenCallWithException_thenRetry() throws Exception {
        // The service will be called 3 times (default maxAttempts)
        doThrow(new RuntimeException("Simulated failure")).when(myService).retryService();
        assertThrows(RuntimeException.class, () -> myService.retryService());
        verify(myService, times(Integer.parseInt(maxAttempts))).retryService(); 
    }

    @Test
    public void givenRetryService_whenCallWithException_thenPrintsRetryCount() throws Exception {
        LogAppender.clearLogMessages();
        
        // FIX for FAILURE 1: Use doAnswer to call the real method and then throw the exception.
        // This ensures the logging inside the real method runs before Spring Retry handles the exception.
        doAnswer(new Answer<Void>() {
            private int count = 0;
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                // Call the real method first to execute the logging statement
                invocation.callRealMethod();
                
                // Throw an exception to force the retry logic unless maxAttempts is reached
                if (count++ < Integer.parseInt(maxAttempts)) {
                     throw new RuntimeException("Simulated failure to trigger retry: " + count);
                }
                
                // This line should technically not be reached if maxAttempts is correct and it threw the last time
                return null;
            }
        }).when(myService).retryService();

        assertThrows(RuntimeException.class, () -> myService.retryService());

        List<String> retryCountLogs = LogAppender.getLogMessages()
          .stream()
          .filter(message -> message.contains("Retry Number:"))
          .collect(Collectors.toList());
        
        // Check if logs were produced at all (addresses "Expected retry logs not found")
        if (retryCountLogs.isEmpty()) {
             throw new AssertionError("Expected retry logs not found after " + Integer.parseInt(maxAttempts) + " calls.");
        }

        // maxAttempts is 3. The retry count is 0, 1, 2. The final log is "Retry Number: 2".
        assertEquals("Retry Number: " + (Integer.parseInt(maxAttempts) - 1), 
            retryCountLogs.get(retryCountLogs.size() - 1), 
            "The last log message should indicate the final retry number.");
            
        verify(myService, times(Integer.parseInt(maxAttempts))).retryService();
    }

    @Test
    public void givenRetryServiceWithRecovery_whenCallWithException_thenRetryRecover() throws Exception {
        doThrow(new SQLException("Simulated DB failure")).when(myService).retryServiceWithRecovery(any());
        myService.retryServiceWithRecovery(null);
        verify(myService, times(Integer.parseInt(maxAttempts))).retryServiceWithRecovery(any());
    }

    @Test
    public void givenRetryServiceWithCustomization_whenCallWithException_thenRetryRecover() throws Exception {
        // FIX for FAILURE 2: The doThrow must be present to force the retry (maxAttempts=2).
        doThrow(new SQLException("Simulated DB failure")).when(myService).retryServiceWithCustomization(any());
        
        myService.retryServiceWithCustomization(null);
        // This method's retry count is 2 due to custom config: 1 initial call + 1 retry.
        verify(myService, times(2)).retryServiceWithCustomization(any());
    }

    @Test
    public void givenRetryServiceWithExternalConfiguration_whenCallWithException_thenRetryRecover() throws Exception {
        doThrow(new SQLException("Simulated DB failure")).when(myService).retryServiceWithExternalConfiguration(any());
        myService.retryServiceWithExternalConfiguration(null);
        verify(myService, times(Integer.parseInt(maxAttempts))).retryServiceWithExternalConfiguration(any());
    }

    // --- RetryTemplate Tests (still require stubbing) ---

    @Test
    public void givenTemplateRetryService_whenCallWithException_thenRetry() throws Exception {
        doThrow(new RuntimeException("Simulated failure")).when(myService).templateRetryService();
        
        assertThrows(RuntimeException.class, () -> {
            retryTemplate.execute(arg0 -> {
                myService.templateRetryService();
                return null;
            });
        });

        verify(myService, times(Integer.parseInt(maxAttempts))).templateRetryService();
    }


    @Test
    public void givenTemplateRetryServiceWithZeroAttempts_whenCallWithException_thenFailImmediately() throws Exception {
        doThrow(new RuntimeException("Simulated failure")).when(myService).templateRetryService();
        
        assertThrows(RuntimeException.class, () -> {
            retryTemplateNoRetry.execute(arg0 -> {
                myService.templateRetryService();
                return null;
            });
        });

        verify(myService, times(1)).templateRetryService();
    }

    // --- Concurrency Limit Test ---
    
    @Test
    public void givenConcurrentLimitService_whenCalledByManyThreads_thenLimitIsEnforced() 
      throws InterruptedException, BrokenBarrierException, TimeoutException { 
        int limit = 5;
        int totalThreads = 10;
        
        // FIX for ERROR: Simplify logic, rely on doAnswer to block and verify the count.
        CountDownLatch releaseLatch = new CountDownLatch(1);
        
        // AtomicInteger to track how many threads *successfully enter the mocked method*
        // This count should equal the concurrency limit.
        final AtomicInteger successfulCalls = new AtomicInteger(0);

        // Mock the service call to block if it succeeds (i.e., if it gets the lock)
        doAnswer((Answer<Void>) invocation -> {
            successfulCalls.incrementAndGet();
            // Wait until the test tells us to release
            releaseLatch.await(5, TimeUnit.SECONDS); 
            return null; // Return successfully after being released
        }).when(myService).concurrentLimitService();
        
        CyclicBarrier startBarrier = new CyclicBarrier(totalThreads + 1); 
        CountDownLatch finishLatch = new CountDownLatch(totalThreads);
        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);

        for (int i = 0; i < totalThreads; i++) {
            executor.submit(() -> {
                try {
                    startBarrier.await();
                    myService.concurrentLimitService(); 
                } catch (Exception e) {
                    if (e instanceof InterruptedException) {
                         Thread.currentThread().interrupt();
                    }
                    // Threads that exceed the limit should fail here.
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        // Wait for all threads to hit the barrier
        startBarrier.await(1, TimeUnit.SECONDS); 
        
        // Give time for the concurrent calls to start and for the concurrency limit to take effect 
        // (5 threads block in the mock, 5 threads are rejected by the aspect).
        Thread.sleep(1000); 

        // Assert that only 'limit' calls successfully entered the *mocked* service method.
        // The remaining (totalThreads - limit) threads were rejected by the ConcurrencyLimit aspect.
        assertEquals(limit, successfulCalls.get(), 
            "Only the defined limit of threads should have successfully entered the service method before rejection.");
        
        // Release the blocking threads
        releaseLatch.countDown();

        // Wait for all threads to finish gracefully
        finishLatch.await(5, TimeUnit.SECONDS); 
        
        executor.shutdownNow();
        
        // Optional: Verify only the limit calls were made, though successfulCalls already covers this
        // verify(myService, times(totalThreads)).concurrentLimitService(); // This is incorrect if ConcurrencyLimit throws an exception on the rejected calls before reaching the mock/spy.
        // The actual verification relies on the successfulCalls counter.
    }
}
