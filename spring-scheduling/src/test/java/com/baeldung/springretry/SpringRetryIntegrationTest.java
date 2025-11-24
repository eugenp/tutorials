package com.baeldung.springretry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test; // JUnit 5 Test
import org.junit.jupiter.api.extension.ExtendWith; // JUnit 5 Extension
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension; // JUnit 5 Spring Extension

import com.baeldung.springretry.logging.LogAppender;

// Use @ExtendWith(SpringExtension.class) to enable Spring context support in JUnit 5
// (While @SpringBootTest includes this, it's good practice to keep it explicit for clarity)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AppConfig.class)
public class SpringRetryIntegrationTestIT {

    // 1. Use @SpyBean: It creates a spy of the MyService bean in the Spring context
    @SpyBean
    private MyService myService;
    
    @Value("${retry.maxAttempts}")
    private String maxAttempts;

    @Autowired
    private RetryTemplate retryTemplate;
    
    @Autowired
    private RetryTemplate retryTemplateNoRetry;

    // --- @Retryable Tests ---
    
    @Test
    public void givenRetryService_whenCallWithException_thenRetry() throws Exception {
        // Use assertThrows instead of @Test(expected = ...)
        doThrow(new RuntimeException("Simulated failure")).when(myService).retryService();
        assertThrows(RuntimeException.class, () -> myService.retryService());
    }

    @Test
    public void givenRetryService_whenCallWithException_thenPrintsRetryCount() throws Exception {
        LogAppender.clearLogMessages();
        doThrow(new RuntimeException("Simulated failure")).when(myService).retryService();

        // Wrap the call in assertThrows
        assertThrows(RuntimeException.class, () -> myService.retryService());

        List<String> retryCountLogs = LogAppender.getLogMessages()
          .stream()
          .filter(message -> message.contains("Retry Number:"))
          .collect(Collectors.toList());

        // Use JUnit 5 assertEquals
        assertEquals("Retry Number: 2", retryCountLogs.get(retryCountLogs.size() - 1));
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
        doThrow(new SQLException("Simulated DB failure")).when(myService).retryServiceWithCustomization(any());
        myService.retryServiceWithCustomization(null);
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
        
        // Use assertThrows to catch the exception thrown by retryTemplate.execute()
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
        
        // Use assertThrows to catch the immediate exception
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
    public void givenConcurrentLimitService_whenCalledByManyThreads_thenLimitIsEnforced() throws InterruptedException {
        int limit = 5;
        int totalThreads = 10;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(totalThreads);
        AtomicInteger activeThreads = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);

        for (int i = 0; i < totalThreads; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await();
                    
                    activeThreads.incrementAndGet(); 
                    myService.concurrentLimitService();
                    activeThreads.decrementAndGet();
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        startLatch.countDown();

        Thread.sleep(200); 

        assertEquals(limit, activeThreads.get());

        finishLatch.await(2, TimeUnit.SECONDS); 
        
        assertEquals(0, activeThreads.get());
        
        executor.shutdownNow();
    }
}
