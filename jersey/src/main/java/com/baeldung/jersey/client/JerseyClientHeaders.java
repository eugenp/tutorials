package com.baeldung.jersey.client;

import com.baeldung.jersey.client.filter.AddHeaderOnRequestFilter;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.client.oauth1.AccessToken;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;
import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.InboundSseEvent;
import javax.ws.rs.sse.SseEventSource;

import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.*;

public class JerseyClientHeaders {

    private static final String BEARER_CONSUMER_SECRET = "my-consumer-secret";
    private static final String BEARER_ACCESS_TOKEN_SECRET = "my-access-token-secret";
    private static final String TARGET = "http://localhost:9998/";
    private static final String MAIN_RESOURCE = "echo-headers";
    private static final String RESOURCE_AUTH_DIGEST = "digest";

    private static String sseHeaderValue;

    public static Response simpleHeader(String headerKey, String headerValue) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(TARGET);
        WebTarget resourceWebTarget = webTarget.path(MAIN_RESOURCE);
        Invocation.Builder invocationBuilder = resourceWebTarget.request();
        invocationBuilder.header(headerKey, headerValue);
        return invocationBuilder.get();
    }

    public static Response simpleHeaderFluently(String headerKey, String headerValue) {
        Client client = ClientBuilder.newClient();
        return client.target(TARGET)
                .path(MAIN_RESOURCE)
                .request()
                .header(headerKey, headerValue)
                .get();
    }

    public static Response basicAuthenticationAtClientLevel(String username, String password) {
        //To simplify we removed de SSL/TLS protection, but it's required to have an encryption
        // when using basic authentication schema as it's send only on Base64 encoding
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);
        Client client = ClientBuilder.newBuilder().register(feature).build();
        return client.target(TARGET)
                .path(MAIN_RESOURCE)
                .request()
                .get();
    }

    public static Response basicAuthenticationAtRequestLevel(String username, String password) {
        //To simplify we removed de SSL/TLS protection, but it's required to have an encryption
        // when using basic authentication schema as it's send only on Base64 encoding
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().build();
        Client client = ClientBuilder.newBuilder().register(feature).build();
        return client.target(TARGET)
                .path(MAIN_RESOURCE)
                .request()
                .property(HTTP_AUTHENTICATION_BASIC_USERNAME, username)
                .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, password)
                .get();
    }

    public static Response digestAuthenticationAtClientLevel(String username, String password) {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.digest(username, password);
        Client client = ClientBuilder.newBuilder().register(feature).build();
        return client.target(TARGET)
                .path(MAIN_RESOURCE)
                .path(RESOURCE_AUTH_DIGEST)
                .request()
                .get();
    }

    public static Response digestAuthenticationAtRequestLevel(String username, String password) {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.digest();

        Client client = ClientBuilder.newBuilder().register(feature).build();
        return client.target(TARGET)
                .path(MAIN_RESOURCE)
                .path(RESOURCE_AUTH_DIGEST)
                .request()
                .property(HTTP_AUTHENTICATION_DIGEST_USERNAME, username)
                .property(HTTP_AUTHENTICATION_DIGEST_PASSWORD, password)
                .get();
    }

    public static Response bearerAuthenticationWithOAuth1AtClientLevel(String token, String consumerKey) {
        ConsumerCredentials consumerCredential = new ConsumerCredentials(consumerKey, BEARER_CONSUMER_SECRET);
        AccessToken accessToken = new AccessToken(token, BEARER_ACCESS_TOKEN_SECRET);
        Feature feature = OAuth1ClientSupport
                .builder(consumerCredential)
                .feature()
                .accessToken(accessToken)
                .build();

        Client client = ClientBuilder.newBuilder().register(feature).build();
        return client.target(TARGET)
                .path(MAIN_RESOURCE)
                .request()
                .get();
    }

    public static Response bearerAuthenticationWithOAuth1AtRequestLevel(String token, String consumerKey) {
        ConsumerCredentials consumerCredential = new ConsumerCredentials(consumerKey, BEARER_CONSUMER_SECRET);
        AccessToken accessToken = new AccessToken(token, BEARER_ACCESS_TOKEN_SECRET);
        Feature feature = OAuth1ClientSupport
                .builder(consumerCredential)
                .feature()
                .build();

        Client client = ClientBuilder.newBuilder().register(feature).build();
        return client.target(TARGET)
                .path(MAIN_RESOURCE)
                .request()
                .property(OAuth1ClientSupport.OAUTH_PROPERTY_ACCESS_TOKEN, accessToken)
                .get();
    }

    public static Response bearerAuthenticationWithOAuth2AtClientLevel(String token) {
        Feature feature = OAuth2ClientSupport.feature(token);

        Client client = ClientBuilder.newBuilder().register(feature).build();
        return client.target(TARGET)
                .path(MAIN_RESOURCE)
                .request()
                .get();
    }

    public static Response bearerAuthenticationWithOAuth2AtRequestLevel(String token, String otherToken) {
        Feature feature = OAuth2ClientSupport.feature(token);

        Client client = ClientBuilder.newBuilder().register(feature).build();
        return client.target(TARGET)
                .path(MAIN_RESOURCE)
                .request()
                .property(OAuth2ClientSupport.OAUTH2_PROPERTY_ACCESS_TOKEN, otherToken)
                .get();
    }

    public static Response filter() {
        Client client = ClientBuilder.newBuilder().register(AddHeaderOnRequestFilter.class).build();
        return client.target(TARGET)
                .path(MAIN_RESOURCE)
                .request()
                .get();
    }

    public static Response sendRestrictedHeaderThroughDefaultTransportConnector(String headerKey, String headerValue) {
        Client client = ClientBuilder.newClient();
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

        return client.target(TARGET)
                .path(MAIN_RESOURCE)
                .request()
                .header(headerKey, headerValue)
                .get();
    }

    public static String simpleSSEHeader() throws InterruptedException {
        Client client = ClientBuilder.newBuilder()
                .register(AddHeaderOnRequestFilter.class)
                .build();

        WebTarget webTarget = client.target(TARGET)
                .path(MAIN_RESOURCE)
                .path("events");

        SseEventSource sseEventSource = SseEventSource.target(webTarget).build();
        sseEventSource.register(JerseyClientHeaders::receiveEvent);
        sseEventSource.open();
        Thread.sleep(3_000);
        sseEventSource.close();

        return sseHeaderValue;
    }

    private static void receiveEvent(InboundSseEvent event) {
        sseHeaderValue = event.readData();
    }
}
