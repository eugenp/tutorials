package com.baeldung.mainclasstest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
public class ApplicationUseMainTest {
    @Test
    public void contextLoads() {
    }
}
