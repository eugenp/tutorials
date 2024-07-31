package com.baeldung.springvault;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
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
