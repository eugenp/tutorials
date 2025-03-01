package com.baeldung;


import com.baeldung.DisableSpringSecurityApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DisableSpringSecurityApplication.class)
public class DisableSpringSecurityApplicationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}

