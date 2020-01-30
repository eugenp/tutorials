package com.baeldung.autoconfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.autoconfiguration.example.AutoconfigurationApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoconfigurationApplication.class)
@EnableJpaRepositories(basePackages = { "com.baeldung.autoconfiguration.example" })
public class SpringContextLiveTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
