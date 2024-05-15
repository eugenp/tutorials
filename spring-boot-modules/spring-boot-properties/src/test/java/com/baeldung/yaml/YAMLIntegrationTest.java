package com.baeldung.yaml;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class)
@TestPropertySource(properties = {"spring.profiles.active = test"})
class YAMLIntegrationTest {

    @Autowired
    private YAMLConfig config;
    
    @Test
    void whenProfileTest_thenNameTesting() {
        assertTrue("testing".equalsIgnoreCase(config.getEnvironment()));
        assertTrue("test-YAML".equalsIgnoreCase(config.getName()));
        assertTrue("myurl".equalsIgnoreCase(config.getComponent().getIdm().getUrl()));
        assertFalse(config.isEnabled());
    }
}
