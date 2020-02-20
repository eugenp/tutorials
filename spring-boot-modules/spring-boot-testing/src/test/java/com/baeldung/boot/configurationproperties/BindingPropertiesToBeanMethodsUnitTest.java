package com.baeldung.boot.configurationproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ServerConfig.class)
@ContextConfiguration(classes = ServerConfigFactory.class)
@TestPropertySource("classpath:server-config-test.properties")
public class BindingPropertiesToBeanMethodsUnitTest {

    @Autowired
    private ServerConfigFactory configFactory;

    @Test
    void givenBeanAnnotatedMethod_whenBindingProperties_thenAllFieldsAreSet() {
        assertEquals("192.168.0.2", configFactory.getDefaultConfigs()
            .getAddress()
            .getIp());

        Map<String, String> expectedResourcesPath = new HashMap<>();
        expectedResourcesPath.put("imgs", "/root/def/imgs");
        assertEquals(expectedResourcesPath, configFactory.getDefaultConfigs()
            .getResourcesPath());
    }
}