package com.baeldung.spring.cloud.azure;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.baeldung.spring.cloud.azure.service.KeyVaultAutoconfiguredClient;
import com.baeldung.spring.cloud.azure.service.KeyVaultClient;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Value("${database.secret.value}")
    private String mySecret;

    private final KeyVaultClient keyVaultClient;

    public Application(@Qualifier(value = "KeyVaultAutoconfiguredClient") KeyVaultAutoconfiguredClient keyVaultAutoconfiguredClient) {
        this.keyVaultClient = keyVaultAutoconfiguredClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);

    }

    @Override
    public void run(String... args) throws Exception {
        KeyVaultSecret keyVaultSecret = keyVaultClient.getSecret("my-secret");
        System.out.println("Hey, our secret is here ->" + keyVaultSecret.getValue());
        System.out.println("Hey, our secret is here from application properties file ->" + mySecret);
    }
}
