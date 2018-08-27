package org.baeldung.springvault;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CredentialsService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "vault.uri=http://localhost:8200", "vault.token=00000000-0000-0000-0000-000000000000" })
public class SpringVaultApplicationUnitTest {

    @Autowired
    private CredentialsService credentialsService;

    /**
     * Test to secure credentials.
     *
     * @throws URISyntaxException
     */
    @Test(expected = ResourceAccessException.class)
    public void testSecureCredentials() throws URISyntaxException {
        // Ideally this test should not throw ResourceAccessException. As the Vault is not configured at the test uri given as property.
        credentialsService.secureCredentials();
    }

    /**
     * Test to access credentials
     * @throws URISyntaxException
     */
    @Test(expected = ResourceAccessException.class)
    public void testAccessCredentials() throws URISyntaxException {
        Credentials credentials = credentialsService.accessCredentials();
        assertNotNull(credentials);
        assertEquals("username", credentials.getUsername());
        assertEquals("password", credentials.getPassword());
    }

}
