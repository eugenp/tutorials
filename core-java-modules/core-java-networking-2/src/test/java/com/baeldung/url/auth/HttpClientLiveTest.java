package com.baeldung.url.auth;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HttpClientLiveTest {

    @Test
    public void sendRequestWithAuthHeader() throws Exception {
        HttpClient httpClient = new HttpClient("user1", "pass1");

        int status = httpClient.sendRequestWithAuthHeader("https://httpbin.org/basic-auth/user1/pass1");

        assertTrue(isSuccess(status));
    }

    @Test
    public void sendRequestWithAuthHeader_whenIncorrectCredentials_thenNotSuccessful() throws Exception {
        HttpClient httpClient = new HttpClient("John", "Smith");

        int status = httpClient.sendRequestWithAuthHeader("https://httpbin.org/basic-auth/user1/pass1");

        assertTrue(isUnauthorized(status));
    }

    @Test
    public void sendRequestWithAuthenticator() throws Exception {
        HttpClient httpClient = new HttpClient("user2", "pass2");

        int status = httpClient.sendRequestWithAuthenticator("https://httpbin.org/basic-auth/user2/pass2");

        assertTrue(isSuccess(status));
    }

    @Test
    public void sendRquestWithAuthenticator_whenIncorrectCredentials_thenNotSuccessful() throws Exception {
        HttpClient httpClient = new HttpClient("John", "Smith");

        int status = httpClient.sendRequestWithAuthenticator("https://httpbin.org/basic-auth/user2/pass2");

        assertTrue(isUnauthorized(status));
    }

    private boolean isSuccess(int status) {
        return (status >= 200) && (status < 300);
    }

    private boolean isUnauthorized(int status) {
        return status == 401;
    }
}
