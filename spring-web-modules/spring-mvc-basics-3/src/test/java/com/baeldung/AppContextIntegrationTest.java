package com.baeldung;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.spring.Application;

@SpringBootTest(classes = Application.class)
public class AppContextIntegrationTest {
    @Test
    public void contextLoads() {
    }
}
