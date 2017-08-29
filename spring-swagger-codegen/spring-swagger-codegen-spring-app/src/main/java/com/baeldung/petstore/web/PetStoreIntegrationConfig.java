package com.baeldung.petstore.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.petstore.client.api.PetApi;
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.auth.OAuth;

@Configuration
public class PetStoreIntegrationConfig {

    @Bean
    public PetApi petpi() {
        return new PetApi(apiClient());
    }
    
    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        
        OAuth petStoreAuth = (OAuth) apiClient.getAuthentication("petstore_auth");
        petStoreAuth.setAccessToken("special-key");

        return apiClient;
    }
    
}
