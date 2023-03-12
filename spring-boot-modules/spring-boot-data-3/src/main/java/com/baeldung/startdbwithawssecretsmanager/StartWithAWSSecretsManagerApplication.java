package com.baeldung.startdbwithawssecretsmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("aws")
public class StartWithAWSSecretsManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartWithAWSSecretsManagerApplication.class, args);
    }

}
