package com.baeldung.springvault;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.annotation.VaultPropertySource;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;

@Configuration
public class VaultTestConfiguration {

    @Bean
    public VaultInitializer vaultInitializer() {
        VaultInitializer vaultInitializer = VaultInitializer.initializeVault();
        return vaultInitializer;
    }

    @Bean
    public VaultTemplate vaultTemplate() throws URISyntaxException {

        VaultInitializer vaultInitializer = vaultInitializer();
        VaultTemplate vaultTemplate = new VaultTemplate(VaultEndpoint.from(new URI("http://localhost:8200")), new TokenAuthentication(vaultInitializer.getRootToken()));
        return vaultTemplate;
    }
}
