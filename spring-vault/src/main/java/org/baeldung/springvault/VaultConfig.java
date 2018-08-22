package org.baeldung.springvault;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;

@Configuration
public class VaultConfig extends AbstractVaultConfiguration {

    @Value("${vault.host}")
    private String host;

    @Value("${vault.port}")
    private int port;

    @Value("${vault.uri}")
    private URI uri;

    @Override
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication("eeee");
    }

    @Override
    public VaultEndpoint vaultEndpoint() { 
        return VaultEndpoint.create(host, port);
    }

}
