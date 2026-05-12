package com.baeldung.auth.server.dynamicscopes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Integrations tests for the {@link DynamicScopesAuthServerApplication}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DynamicScopesAuthServerUnitTest {

    @LocalServerPort
    int port;

    private RestTestClient restTestClient;

    @Autowired
    ApplicationContext ctx;

    @Test
    void whenStandardRequest_thenSuccess() {

        //

    }

    @BeforeEach
    void setupRestClient() {
        restTestClient = RestTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

}