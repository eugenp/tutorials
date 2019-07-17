package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.activitiwithspring.ActivitiWithSpringApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiWithSpringApplication.class)
@AutoConfigureTestDatabase
public class SpringContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
