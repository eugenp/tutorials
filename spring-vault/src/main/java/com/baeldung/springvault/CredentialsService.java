package com.baeldung.springvault;

import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
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
    private final VaultTemplate vaultTemplate;
    private final VaultKeyValueOperations vaultKeyValueOperations;
    private final CredentialsRepository credentialsRepository;

    @Autowired
    public CredentialsService(VaultTemplate vaultTemplate, CredentialsRepository credentialsRepository) {
        this.vaultTemplate = vaultTemplate;
        this.credentialsRepository = credentialsRepository;
        this.vaultKeyValueOperations = vaultTemplate.opsForKeyValue("credentials/myapp", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2);
    }

    /**
     *  To Secure Credentials
     * @param credentials
     * @return VaultResponse
     * @throws URISyntaxException
     */
    public void secureCredentials(Credentials credentials) {
        vaultKeyValueOperations.put(credentials.getUsername(), credentials);
    }

    /**
     * To Retrieve Credentials
     * @return Credentials
     */
    public Credentials accessCredentials(String username) {
        VaultResponseSupport<Credentials> response = vaultKeyValueOperations.get(username, Credentials.class);
        return response.getData();
    }

    public Credentials saveCredentials(Credentials credentials) {
        return credentialsRepository.save(credentials);
    }

    public Optional<Credentials> findById(String username) {
        return credentialsRepository.findById(username);
    }
}
