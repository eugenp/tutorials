package com.baeldung;

import com.baeldung.security.SecurityJavaConfig;
import com.baeldung.spring.ClientWebConfig;
import com.baeldung.spring.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ClientWebConfig.class, SecurityJavaConfig.class, WebConfig.class })
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
