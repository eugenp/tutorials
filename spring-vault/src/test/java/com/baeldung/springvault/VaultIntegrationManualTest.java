package com.baeldung.springvault;

import static com.baeldung.springvault.VaultInitializer.API_VERSION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URISyntaxException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Assume;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.repository.configuration.EnableVaultRepositories;

/**
 * These tests are requiring the {@code vault} command to be installed and available in the executing
 * platform. So, if you intend to run them in your environment, the please install the vault and then
 * run the ignored tests.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CredentialsService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = VaultTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@EnableVaultRepositories
public class VaultIntegrationManualTest {
    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private VaultTemplate vaultTemplate;

    private CredentialsService credentialsService;

    @Before
    public void setup() {
        this.credentialsService = new CredentialsService(vaultTemplate, credentialsRepository);
    }

    /**
     * Test to secure credentials.
     *
     * @throws URISyntaxException
     */
    @Test
    public void givenCredentials_whenSecureCredentials_thenCredentialsSecuredSuccessfully() throws URISyntaxException {
        // Given
        Credentials credentials = new Credentials("username", "password");
        // When
        credentialsService.secureCredentials(credentials);
        // Then
        Credentials storedCredentials = credentialsService.accessCredentials(credentials.getUsername());
        Assertions.assertNotNull(storedCredentials);
        Assertions.assertEquals(credentials.getUsername(), storedCredentials.getUsername());
        Assertions.assertEquals(credentials.getPassword(), storedCredentials.getPassword());
    }

    @Test
    public void givenCredentials_whenSave_thenReturnCredentials() throws InterruptedException {
        Assume.assumeTrue("v1".equals(API_VERSION));

        credentialsService = new CredentialsService(vaultTemplate, credentialsRepository);
        // Given
        Credentials credentials = new Credentials("login", "password");

        // When
        Credentials savedCredentials = credentialsService.saveCredentials(credentials);

        // Then
        assertNotNull(savedCredentials);
        assertEquals(credentials.getUsername(), savedCredentials.getUsername());
        assertEquals(credentials.getPassword(), savedCredentials.getPassword());
    }

    @Test
    public void givenId_whenFindById_thenReturnCredentials() {
        // Given
        Assume.assumeTrue("v1".equals(API_VERSION));
        Credentials expectedCredentials = new Credentials("login", "p@ssw@rd");
        credentialsService.saveCredentials(expectedCredentials);

        // When
        Optional<Credentials> retrievedCredentials = credentialsService.findById(expectedCredentials.getUsername());

        // Then
        assertNotNull(retrievedCredentials);
        assertNotNull(retrievedCredentials.get());
        assertEquals(expectedCredentials.getUsername(), retrievedCredentials.get()
            .getUsername());
        assertEquals(expectedCredentials.getPassword(), retrievedCredentials.get()
            .getPassword());
    }

}
