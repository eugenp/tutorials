package com.baeldung.springretry.test;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.springretry.MyService;
import com.baeldung.springretry.MyServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SpringRetryDeclarativeTest {
    @Configuration
    @EnableRetry
    public static class AppConfig {
        @Bean
        public MyService myService() {
            return new MyServiceImpl();
        }
    }

    @Autowired
    private MyService myService;

    @Test(expected = RuntimeException.class)
    public void defaultRetry() {
        myService.defaultRetryService();
    }

    @Test
    public void customRetryServiceIOException() throws SQLException {
        myService.customRetryService(null);
    }
}
