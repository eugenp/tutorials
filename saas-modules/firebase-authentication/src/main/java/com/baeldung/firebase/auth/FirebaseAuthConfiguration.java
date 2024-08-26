package com.baeldung.firebase.auth;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

@Configuration
@EnableConfigurationProperties(FirebaseConfigurationProperties.class)
public class FirebaseAuthConfiguration {

    private final FirebaseConfigurationProperties firebaseConfigurationProperties;

    public FirebaseAuthConfiguration(FirebaseConfigurationProperties firebaseConfigurationProperties) {
        this.firebaseConfigurationProperties = firebaseConfigurationProperties;
    }

    @Bean
    public FirebaseAuth firebaseAuth() throws IOException {
        byte[] privateKey = firebaseConfigurationProperties.getPrivateKey().getContentAsByteArray();
        InputStream credentials = new ByteArrayInputStream(privateKey);
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(credentials))
            .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
        return FirebaseAuth.getInstance(firebaseApp);
    }

}