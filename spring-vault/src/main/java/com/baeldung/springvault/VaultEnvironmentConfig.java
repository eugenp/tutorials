package com.baeldung.springvault;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.config.EnvironmentVaultConfiguration;

/**
 * Example class to configure Vault beans using EnvironmentVaultConfiguration
 *
 */
@Configuration
@PropertySource(value = { "vault-config.properties" })
@Import(value = EnvironmentVaultConfiguration.class)
public class VaultEnvironmentConfig {

}
