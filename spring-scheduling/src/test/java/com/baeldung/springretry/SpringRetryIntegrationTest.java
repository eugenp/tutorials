package com.baeldung.springretry;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class SpringRetryIntegrationTest {

    @SpyBean
    private MyService myService;
    @Value("${retry.maxAttempts}")
    private String maxAttempts;

    @Autowired
    private RetryTemplate retryTemplate;

    @Test(expected = RuntimeException.class)
    public void givenRetryService_whenCallWithException_thenRetry() {
        myService.retryService();
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
}
