package com.baeldung.firebase.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Validated
@ConfigurationProperties(prefix = "com.baeldung.firebase")
public class FirebaseConfigurationProperties {

    @NotNull
    private Resource privateKey;

    @NotBlank
    private String webApiKey;

    public Resource getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(Resource privateKey) {
        this.privateKey = privateKey;
    }

    public String getWebApiKey() {
        return webApiKey;
    }

    public void setWebApiKey(String webApiKey) {
        this.webApiKey = webApiKey;
    }

}