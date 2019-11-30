package com.baeldung.openid.oidc.login;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.openid.oidc.login.SpringOidcLoginApplication;

@SpringBootTest(classes = SpringOidcLoginApplication.class)
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
