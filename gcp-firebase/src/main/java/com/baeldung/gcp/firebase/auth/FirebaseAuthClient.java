package com.baeldung.gcp.firebase.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class FirebaseAuthClient {

    private static final String API_KEY_PARAM = "key";
    private static final String INVALID_CREDENTIALS_ERROR = "INVALID_LOGIN_CREDENTIALS";
    private static final String BASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword";

    private final String webApiKey;

    public FirebaseAuthClient(@Value("${com.baeldung.firebase.web-api-key}") String webApiKey) {
        this.webApiKey = webApiKey;
    }

    public String login(String emailId, String password) {
        FirebaseSignInRequest requestBody = new FirebaseSignInRequest(emailId, password, true);
        FirebaseSignInResponse response = sendSignInRequest(requestBody);
        return response.idToken();
    }

    private FirebaseSignInResponse sendSignInRequest(FirebaseSignInRequest firebaseSignInRequest) {
        try {
            return RestClient.create(BASE_URL)
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
    }

    record FirebaseSignInRequest(String email, String password, boolean returnSecureToken) {
    }

    record FirebaseSignInResponse(String idToken) {
    }

}