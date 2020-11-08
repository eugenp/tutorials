package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.baeldung.springsecuredsockets.config.AppConfig;
import com.baeldung.springsecuredsockets.config.DataStoreConfig;
import com.baeldung.springsecuredsockets.config.SecurityConfig;
import com.baeldung.springsecuredsockets.config.SocketBrokerConfig;
import com.baeldung.springsecuredsockets.config.SocketSecurityConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, DataStoreConfig.class, SecurityConfig.class,
		SocketBrokerConfig.class, SocketSecurityConfig.class })
@WebAppConfiguration
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
