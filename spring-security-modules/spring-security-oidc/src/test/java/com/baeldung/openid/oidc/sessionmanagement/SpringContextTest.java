package com.baeldung.openid.oidc.sessionmanagement;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.baeldung.openid.oidc.utils.YamlLoaderInitializer;

//We'll ignore this test, as we don't want to depend on the Okta instance OIDC-configuration endpoint to be available
@Disabled
@SpringBootTest(classes = SpringOidcSessionManagementApplication.class, properties = "custom.configyaml.file=sessionmanagement-application.yml")
@ContextConfiguration(initializers = YamlLoaderInitializer.class)
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
