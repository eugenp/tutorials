package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.spring.session.SessionWebApplication;

/**
 * This live test requires:
 * redis instance running on the environment
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SessionWebApplication.class)
public class SpringContextLiveTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
