package com.baeldung.gcp.firebase.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class FirebaseAuthClient {

    private static final String API_KEY_PARAM = "key";
    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";

    private static final String INVALID_CREDENTIALS_ERROR = "INVALID_LOGIN_CREDENTIALS";
    private static final String INVALID_REFRESH_TOKEN_ERROR = "INVALID_REFRESH_TOKEN";

    private static final String SIGN_IN_BASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword";
    private static final String REFRESH_TOKEN_BASE_URL = "https://securetoken.googleapis.com/v1/token";

    private final String webApiKey;

    public FirebaseAuthClient(@Value("${com.baeldung.firebase.web-api-key}") String webApiKey) {
        this.webApiKey = webApiKey;
    }

    public FirebaseSignInResponse login(String emailId, String password) {
        FirebaseSignInRequest requestBody = new FirebaseSignInRequest(emailId, password, true);
        return sendSignInRequest(requestBody);
    }

    public RefreshTokenResponse exchangeRefreshToken(String refreshToken) {
        RefreshTokenRequest requestBody = new RefreshTokenRequest(REFRESH_TOKEN_GRANT_TYPE, refreshToken);
        return sendRefreshTokenRequest(requestBody);
    }

    private FirebaseSignInResponse sendSignInRequest(FirebaseSignInRequest firebaseSignInRequest) {
        try {
            return RestClient.create(SIGN_IN_BASE_URL)
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

    private RefreshTokenResponse sendRefreshTokenRequest(RefreshTokenRequest refreshTokenRequest) {
        try {
            return RestClient.create(REFRESH_TOKEN_BASE_URL)
                .post()
                .uri(uriBuilder -> uriBuilder
                    .queryParam(API_KEY_PARAM, webApiKey)
                    .build())
                .body(refreshTokenRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(RefreshTokenResponse.class);
        } catch (HttpClientErrorException exception) {
            if (exception.getResponseBodyAsString().contains(INVALID_REFRESH_TOKEN_ERROR)) {
                throw new InvalidRefreshTokenException("Invalid refresh token provided");
            }
            throw exception;
        }
    }

    record FirebaseSignInRequest(String email, String password, boolean returnSecureToken) {
    }

    record FirebaseSignInResponse(String idToken, String refreshToken) {
    }

    record RefreshTokenRequest(String grant_type, String refresh_token) {
    }

    record RefreshTokenResponse(String id_token) {
    }

}