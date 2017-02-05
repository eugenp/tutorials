package org.baeldung.springretry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class SpringRetryIntegrationTest {

    @Autowired
    private MyService myService;

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

    @Test(expected = RuntimeException.class)
    public void givenTemplateRetryService_whenCallWithException_thenRetry() {
        retryTemplate.execute(arg0 -> {
            myService.templateRetryService();
            return null;
        });
    }
}
