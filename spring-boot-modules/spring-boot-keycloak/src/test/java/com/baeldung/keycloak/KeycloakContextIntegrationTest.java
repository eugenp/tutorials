package com.baeldung.keycloak;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.baeldung.keycloak.SpringBoot;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { SpringBoot.class })
public class KeycloakContextIntegrationTest {

    @Test
    public void whenLoadApplication_thenSuccess() {

    }

}
