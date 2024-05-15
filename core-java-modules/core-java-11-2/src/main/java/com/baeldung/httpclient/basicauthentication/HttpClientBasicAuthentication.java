package com.baeldung.httpclient.basicauthentication;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientBasicAuthentication {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientBasicAuthentication.class);

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        useClientWithAuthenticator();
        useClientWithHeaders();
    }

    private static void useClientWithAuthenticator() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
            .authenticator(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("postman", "password".toCharArray());
                }
            })
            .build();

        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(new URI("https://postman-echo.com/basic-auth"))
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        logger.info("Status using authenticator {}", response.statusCode());
    }

    private static void useClientWithHeaders() throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newBuilder()
            .build();

        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(new URI("https://postman-echo.com/basic-auth"))
            .header("Authorization", getBasicAuthenticationHeader("postman", "password"))
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        logger.info("Status using headers: {}", response.statusCode());
    }

    private static final String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder()
            .encodeToString(valueToEncode.getBytes());
    }

}
