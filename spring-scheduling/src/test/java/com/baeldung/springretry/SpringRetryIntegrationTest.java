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
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.springretry.logging.LogAppender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class SpringRetryIntegrationTest {

    @SpyBean
    private MyService myService;
    @Value("${retry.maxAttempts}")
    private String maxAttempts;

    @Autowired
    private RetryTemplate retryTemplate;
    
    @Autowired
    private RetryTemplate retryTemplateNoRetry;  


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

        assertEquals("Retry Number: 2", retryCountLogs.get(retryCountLogs.size() - 1));
    }

    @Test
    public void givenRetryServiceWithRecovery_whenCallWithException_thenRetryRecover() throws SQLException {
        myService.retryServiceWithRecovery(null);
    }

    @Test
    public void givenRetryServiceWithCustomization_whenCallWithException_thenRetryRecover() throws SQLException {
        myService.retryServiceWithCustomization(null);
        verify(myService, times(Integer.parseInt(maxAttempts))).retryServiceWithCustomization(any());
    }

    @Test
    public void givenRetryServiceWithExternalConfiguration_whenCallWithException_thenRetryRecover() throws SQLException {
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
        retryTemplateNoAttempts.execute(arg0 -> {
            myService.templateRetryService();
            return null;
        });
        verify(myService, times(1)).templateRetryService(); 
    }
}
