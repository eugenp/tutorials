package com.baeldung.springvaultk8s;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.KubernetesAuthentication;
import org.springframework.vault.authentication.KubernetesAuthenticationOptions;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class VaultConfig extends AbstractVaultConfiguration {

    private final Environment env;
    private final RestOperations restOperations;

    public VaultConfig(Environment env, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.env = env;
    }

    @Override
    public VaultEndpoint vaultEndpoint() {
        try {
            return VaultEndpoint.from(new URI(env.getProperty("vault.addr", "http://localhost:8200")));
        }
        catch(URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        KubernetesAuthenticationOptions options = KubernetesAuthenticationOptions.builder()
          .jwtSupplier(() -> env.getProperty("vault.jwt.token"))
          .role(env.getProperty("vault.jwt.role","default"))
          .build();
        return new KubernetesAuthentication(options,restOperations);
    }
}
