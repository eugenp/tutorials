package com.baeldung.springvault;

import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    private VaultTemplate vaultTemplate;
    
    @Autowired
    private CredentialsRepository credentialsRepository;

    /**
    *  To Secure Credentials
    * @param credentials
    * @return VaultResponse
    * @throws URISyntaxException
    */
    public void secureCredentials(Credentials credentials) throws URISyntaxException {

        vaultTemplate.write("credentials/myapp", credentials);
    }

    /**
     * To Retrieve Credentials
     * @return Credentials
     * @throws URISyntaxException
     */
    public Credentials accessCredentials() throws URISyntaxException {

        VaultResponseSupport<Credentials> response = vaultTemplate.read("credentials/myapp", Credentials.class);
        return response.getData();
    }
    
    public Credentials saveCredentials(Credentials credentials) {

        return credentialsRepository.save(credentials);
    }

    public Optional<Credentials> findById(String username) {

        return credentialsRepository.findById(username);
    }

}
