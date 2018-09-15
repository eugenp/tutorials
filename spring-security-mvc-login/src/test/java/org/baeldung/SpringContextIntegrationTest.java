package org.baeldung;

import org.baeldung.spring.ChannelSecSecurityConfig;
import org.baeldung.spring.MvcConfig;
import org.baeldung.spring.RedirectionSecurityConfig;
import org.baeldung.spring.SecSecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MvcConfig.class, ChannelSecSecurityConfig.class, RedirectionSecurityConfig.class,
		SecSecurityConfig.class })
@WebAppConfiguration
public class SpringContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
