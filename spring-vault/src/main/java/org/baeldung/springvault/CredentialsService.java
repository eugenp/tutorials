package org.baeldung.springvault;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

/**
 * Sample service to demonstrate storing and retrieval of secrets.
 * 
 * NOTE: We need to configure Vault and provide the Vault uri in the properties file.
 *
 */
@Service
public class CredentialsService {

    @Value("${vault.token}")
    private String token;

    @Value("${vault.uri}")
    private String vaultUri;;

    /**
     * To Secure Credentials
     * @throws URISyntaxException
     */
    public void secureCredentials() throws URISyntaxException {
        Credentials secrets = new Credentials("username", "password");
        VaultTemplate vaultTemplate = new VaultTemplate(VaultEndpoint.from(new URI(vaultUri)), new TokenAuthentication(token));
        vaultTemplate.write("secret/myapp", secrets);
    }

    /**
     * To Retrieve Credentials
     * @return
     * @throws URISyntaxException
     */
    public Credentials accessCredentials() throws URISyntaxException {
        VaultTemplate vaultTemplate = new VaultTemplate(VaultEndpoint.from(new URI(vaultUri)), new TokenAuthentication(token));
        VaultResponseSupport<Credentials> response = vaultTemplate.read("secret/myapp", Credentials.class);
        return response.getData();
    }
}
