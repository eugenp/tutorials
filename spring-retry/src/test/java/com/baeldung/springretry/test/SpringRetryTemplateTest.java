package com.baeldung.springretry.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.springretry.DefaultListenerSupport;
import com.baeldung.springretry.MyService;
import com.baeldung.springretry.MyServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SpringRetryTemplateTest {

    @Configuration
    @EnableRetry
    public static class AppConfig {
        @Bean
        public MyService myService() {
            return new MyServiceImpl();
        }

        @Bean
        public DefaultListenerSupport defaultListenerSupport() {
            return new DefaultListenerSupport();
        }

        @Bean
        public RetryTemplate retryTemplate() {
            RetryTemplate retryTemplate = new RetryTemplate();

            FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
            fixedBackOffPolicy.setBackOffPeriod(2000l);
            retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

            SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
            retryPolicy.setMaxAttempts(2);
            retryTemplate.setRetryPolicy(retryPolicy);

            retryTemplate.registerListener(new DefaultListenerSupport());
            return retryTemplate;
        }
    }

    @Autowired
    private RetryTemplate retryTemplate;

    @Autowired
    private MyService myService;

    @Test(expected = RuntimeException.class) 
    public void customRetryServiceIOException() {
        retryTemplate.execute(new RetryCallback<Void, RuntimeException>() {
            @Override
            public Void doWithRetry(RetryContext arg0) {
                myService.templateRetryService();
                return null;
            }
        });

    }
}
