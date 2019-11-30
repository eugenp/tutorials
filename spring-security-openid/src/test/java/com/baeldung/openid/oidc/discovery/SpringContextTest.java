package com.baeldung.openid.oidc.discovery;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//We'll ignore this test, as we don't want to depend on Google's OIDC-configuration endpoint to be available
@Disabled
@SpringBootTest(classes = SpringOidcDiscoveryApplication.class)
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
