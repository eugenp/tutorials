package com.baeldung.firebase.auth;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
@EnableConfigurationProperties(FirebaseConfigurationProperties.class)
public class FirebaseAuthClient {

    private static final String API_KEY_PARAM = "key";
    private static final String INVALID_CREDENTIALS_ERROR = "INVALID_LOGIN_CREDENTIALS";
    private static final String BASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword";

    private final FirebaseConfigurationProperties firebaseConfigurationProperties;

    public FirebaseAuthClient(FirebaseConfigurationProperties firebaseConfigurationProperties) {
        this.firebaseConfigurationProperties = firebaseConfigurationProperties;
    }

    public String login(UserLoginRequest userLoginRequest) {
        FirebaseSignInRequest requestBody = prepareRequestBody(userLoginRequest);
        FirebaseSignInResponse response = sendSignInRequest(requestBody);
        return response.idToken();
    }

    private FirebaseSignInResponse sendSignInRequest(FirebaseSignInRequest firebaseSignInRequest) {
        String webApiKey = firebaseConfigurationProperties.getWebApiKey();
        final FirebaseSignInResponse response;
        
        try {
            response = RestClient.create(BASE_URL)
                .post()
                .uri(uriBuilder -> uriBuilder
                    .queryParam(API_KEY_PARAM, webApiKey)
                    .build())
                .body(firebaseSignInRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(FirebaseSignInResponse.class);
        } catch (HttpClientErrorException exception) {
            if (exception.getResponseBodyAsString().contains(INVALID_CREDENTIALS_ERROR)) {
                throw new InvalidLoginCredentialsException("Invalid login credentials provided");
            }
            throw exception;
        }
        
        return response;
    }

    private FirebaseSignInRequest prepareRequestBody(UserLoginRequest userLoginRequest) {
        return new FirebaseSignInRequest(userLoginRequest.emailId(), userLoginRequest.password(), true);
    }

    record UserLoginRequest(String emailId, String password) {
    }

    record FirebaseSignInRequest(String email, String password, boolean returnSecureToken) {
    }

    record FirebaseSignInResponse(String idToken) {
    }

}