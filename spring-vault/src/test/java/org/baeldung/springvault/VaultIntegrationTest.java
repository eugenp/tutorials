package org.baeldung.springvault;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.vault.support.VaultResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CredentialsService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestPropertySource(properties = { "vault.uri=http://localhost:8200" }) // , "vault.token=ed024b88-f807-0226-4550-b8a97de2a02e" })
public class VaultIntegrationTest {

    @Autowired
    private CredentialsService credentialsService;

    private VaultInitializer vaultInitializer;

    @Before
    public void setUp() {

        vaultInitializer = VaultInitializer.init();
        System.setProperty("vault.token", vaultInitializer.getRootToken());
        System.setProperty("vault.uri", "http://localhost:8200");
    }

    @After
    public void close() {
        try {
            vaultInitializer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test to secure credentials.
     *
     * @throws URISyntaxException
     */
    @Test
    @Ignore
    public void givenCredentials_whenSecureCredentials_thenCredentialsSecured() throws URISyntaxException {
        try {
            // Given
            Credentials credentials = new Credentials("username", "password");

            // When
            VaultResponse response = credentialsService.secureCredentials(credentials);

            // Then
            assertNotNull(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Test to access credentials
     * @throws URISyntaxException
     */
    @Test
    @Ignore
    public void whenAccessCredentials_thenCredentialsRetrieved() throws URISyntaxException {

        // Given
        Credentials credentials = credentialsService.accessCredentials();

        // Then
        assertNotNull(credentials);
        assertEquals("username", credentials.getUsername());
        assertEquals("password", credentials.getPassword());
    }

}
