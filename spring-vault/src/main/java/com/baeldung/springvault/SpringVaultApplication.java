package com.baeldung.springvault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.vault.repository.configuration.EnableVaultRepositories;

/**
 * Sample application to demonstrate basics of Spring Vault
 *
 */
@SpringBootApplication
@EnableVaultRepositories
public class SpringVaultApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringVaultApplication.class, args);
    }

}
