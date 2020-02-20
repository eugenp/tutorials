package com.baeldung.boot.configurationproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@EnableConfigurationProperties(value = ServerConfig.class)
@ActiveProfiles("test")
public class BindingYMLPropertiesUnitTest {

    @Autowired
    private ServerConfig serverConfig;

    @Test
    void whenBindingYMLConfigFile_thenAllFieldsAreSet() {
        assertEquals("192.168.0.4", serverConfig.getAddress()
            .getIp());

        Map<String, String> expectedResourcesPath = new HashMap<>();
        expectedResourcesPath.put("imgs", "/etc/test/imgs");
        assertEquals(expectedResourcesPath, serverConfig.getResourcesPath());
    }
}