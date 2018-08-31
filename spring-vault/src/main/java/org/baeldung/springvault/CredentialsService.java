package org.baeldung.springvault;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.env.VaultPropertySource;
import org.springframework.vault.support.VaultResponse;
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
    private String vaultUri;

    /**
    *  To Secure Credentials
    * @param credentials
    * @return VaultResponse
    * @throws URISyntaxException
    */
    public VaultResponse secureCredentials(Credentials credentials) throws URISyntaxException {

        VaultTemplate vaultTemplate = new VaultTemplate(VaultEndpoint.from(new URI(vaultUri)), new TokenAuthentication(token));
        VaultResponse vaultResponse = vaultTemplate.write("secret/myapp", credentials);
        return vaultResponse;
    }

    /**
     * To Retrieve Credentials
     * @return Credentials
     * @throws URISyntaxException
     */
    public Credentials accessCredentials() throws URISyntaxException {
        VaultTemplate vaultTemplate = new VaultTemplate(VaultEndpoint.from(new URI(vaultUri)), new TokenAuthentication(token));
        VaultResponseSupport<Credentials> response = vaultTemplate.read("secret/myapp", Credentials.class);
        return response.getData();
    }
}
