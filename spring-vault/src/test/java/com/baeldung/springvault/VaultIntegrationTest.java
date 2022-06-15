package com.baeldung.springvault;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URISyntaxException;
import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * These tests are requiring the {@code vault} command to be installed and available in the executing
 * platform. So, if you intend to run them in your environment, the please install the vault and then
 * run the ignored tests.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CredentialsService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = VaultTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class VaultIntegrationTest {

    @Autowired
    private CredentialsService credentialsService;

    @MockBean
    private CredentialsRepository credentialsRepository;

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
            credentialsService.secureCredentials(credentials);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Test to access credentials
     *
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

    @Test
    @Ignore
    public void givenCredentials_whenSave_thenReturnCredentials() {
        // Given
        Credentials credentials = new Credentials("login", "password");
        Mockito.when(credentialsRepository.save(credentials))
            .thenReturn(credentials);

        // When
        Credentials savedCredentials = credentialsService.saveCredentials(credentials);

        // Then
        assertNotNull(savedCredentials);
        assertEquals(savedCredentials.getUsername(), credentials.getUsername());
        assertEquals(savedCredentials.getPassword(), credentials.getPassword());
    }

    @Test
    @Ignore
    public void givenId_whenFindById_thenReturnCredentials() {
        // Given
        Credentials credentials = new Credentials("login", "p@ssw@rd");
        Mockito.when(credentialsRepository.findById("login"))
            .thenReturn(Optional.of(credentials));

        // When
        Optional<Credentials> returnedCredentials = credentialsService.findById("login");

        // Then
        assertNotNull(returnedCredentials);
        assertNotNull(returnedCredentials.get());
        assertEquals(returnedCredentials.get()
            .getUsername(), credentials.getUsername());
        assertEquals(returnedCredentials.get()
            .getPassword(), credentials.getPassword());
    }

}
