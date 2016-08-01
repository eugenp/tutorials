package com.baeldung.spring.cloud.config.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class) @SpringApplicationConfiguration(classes = ConfigServer.class) @WebAppConfiguration public class ConfigServerTests {
        @Test public void contextLoads() {
        }
}
