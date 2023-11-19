package com.baeldung.startdbwithawssecretsmanager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Profile("aws")
public class StartWithAWSSecretsManagerApplication {
    @Value("${api-key1}")
    private String apiKeyValue1;

    @Value("${api-key2}")
    private String apiKeyValue2;

    @PostConstruct
    private void postConstruct() {
        System.out.println(apiKeyValue1);
        System.out.println(apiKeyValue2);
    }

    public static void main(String[] args) {
        SpringApplication.run(StartWithAWSSecretsManagerApplication.class, args);
    }

}
