package com.baeldung.springcloudvault;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("vault")
@SpringBootTest(classes = SpringCloudVaultTestApplication.class)
public class SpringCloudVaultApplicationLiveTest {

    @Autowired
    Environment env;

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
        assertNotNull(env.getProperty("foo"));
    }

}
