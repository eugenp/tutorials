package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.baeldung.spring.configuration.ApplicationConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class})
@WebAppConfiguration
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}

