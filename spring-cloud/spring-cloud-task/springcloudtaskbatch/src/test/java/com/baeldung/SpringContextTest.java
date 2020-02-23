package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.task.JobConfiguration;
import com.baeldung.task.TaskDemo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ContextConfiguration(classes = { JobConfiguration.class, TaskDemo.class }, initializers = { ConfigFileApplicationContextInitializer.class })
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
