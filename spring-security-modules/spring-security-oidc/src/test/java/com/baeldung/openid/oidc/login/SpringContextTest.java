package com.baeldung.openid.oidc.login;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.baeldung.openid.oidc.utils.YamlLoaderInitializer;

@SpringBootTest(classes = SpringOidcLoginApplication.class, properties = "custom.configyaml.file=login-application.yml")
@ContextConfiguration(initializers = YamlLoaderInitializer.class)
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
