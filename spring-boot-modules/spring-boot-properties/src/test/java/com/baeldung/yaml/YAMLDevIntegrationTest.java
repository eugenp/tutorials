package com.baeldung.yaml;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class)
@TestPropertySource(properties = {"spring.profiles.active = dev"})
class YAMLDevIntegrationTest {

    @Autowired
    private YAMLConfig config;
    
    @Test
    void whenProfileTest_thenNameTesting() {
        assertTrue("development".equalsIgnoreCase(config.getEnvironment()));
        assertTrue("dev-YAML".equalsIgnoreCase(config.getName()));
    }
}
