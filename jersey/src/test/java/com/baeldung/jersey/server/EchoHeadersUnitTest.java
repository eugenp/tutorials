package com.baeldung.jersey.server;

import com.baeldung.jersey.client.JerseyClientHeaders;
import com.baeldung.jersey.client.filter.AddHeaderOnRequestFilter;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EchoHeadersUnitTest extends JerseyTest {

    private static final String SIMPLE_HEADER_KEY = "my-header-key";
    private static final String SIMPLE_HEADER_VALUE = "my-header-value";
    private static final String USERNAME = "baeldung";
    private static final String PASSWORD = "super-secret";
    private static final String AUTHORIZATION_HEADER_KEY = "authorization";
    private static final String BEARER_TOKEN_VALUE = "my-token";
    private static final String BEARER_CONSUMER_KEY_VALUE = "my-consumer-key";
    private static final String BEARER_REQUEST_TOKEN_VALUE = "my-request-token";

    @Test
    public void whenCallingSimpleHeader_thenHeadersReturnedBack() {
        Response response = JerseyClientHeaders.simpleHeader(SIMPLE_HEADER_KEY, SIMPLE_HEADER_VALUE);

        assertEquals(response.getHeaderString(SIMPLE_HEADER_KEY), SIMPLE_HEADER_VALUE);
    }

    @Test
    public void whenCallingSimpleHeaderFluently_thenHeadersReturnedBack() {
        Response response = JerseyClientHeaders.simpleHeaderFluently(SIMPLE_HEADER_KEY, SIMPLE_HEADER_VALUE);

        assertEquals(response.getHeaderString(SIMPLE_HEADER_KEY), SIMPLE_HEADER_VALUE);
    }

    @Test
    public void whenCallingBasicAuthenticationAtClientLevel_thenHeadersReturnedBack() {
        Response response = JerseyClientHeaders.basicAuthenticationAtClientLevel(USERNAME, PASSWORD);

        assertBasicAuthenticationHeaders(response);
    }

    @Test
    public void whenCallingBasicAuthenticationAtRequestLevel_thenHeadersReturnedBack() {
        Response response = JerseyClientHeaders.basicAuthenticationAtRequestLevel(USERNAME, PASSWORD);

        assertBasicAuthenticationHeaders(response);
    }

    @Test
    public void whenCallingDigestAuthenticationAtClientLevel_thenHeadersReturnedBack() {
        Response response = JerseyClientHeaders.digestAuthenticationAtClientLevel(USERNAME, PASSWORD);

        Map<String, String> subHeadersMap = parseAuthenticationSubHeader(response, 7);

        assertDigestAuthenticationHeaders(subHeadersMap);
    }

    @Test
    public void whenCallingDigestAuthenticationAtRequestLevel_thenHeadersReturnedBack() {
        Response response = JerseyClientHeaders.digestAuthenticationAtRequestLevel(USERNAME, PASSWORD);

        Map<String, String> subHeadersMap = parseAuthenticationSubHeader(response, 7);

        assertDigestAuthenticationHeaders(subHeadersMap);
    }

    @Test
    public void whenCallingBearerAuthenticationWithOAuth1AtClientLevel_thenHeadersReturnedBack() {
        Response response = JerseyClientHeaders.bearerAuthenticationWithOAuth1AtClientLevel(BEARER_TOKEN_VALUE, BEARER_CONSUMER_KEY_VALUE);

        Map<String, String> subHeadersMap = parseAuthenticationSubHeader(response, 6);

        assertBearerAuthenticationHeaders(subHeadersMap);
    }

    @Test
    public void whenCallingBearerAuthenticationWithOAuth1AtRequestLevel_thenHeadersReturnedBack() {
        Response response = JerseyClientHeaders.bearerAuthenticationWithOAuth1AtRequestLevel(BEARER_TOKEN_VALUE, BEARER_CONSUMER_KEY_VALUE);

        Map<String, String> subHeadersMap = parseAuthenticationSubHeader(response, 6);

        assertBearerAuthenticationHeaders(subHeadersMap);
    }

    @Test
    public void whenCallingBearerAuthenticationWithOAuth2AtClientLevel_thenHeadersReturnedBack() {
        Response response = JerseyClientHeaders.bearerAuthenticationWithOAuth2AtClientLevel(BEARER_TOKEN_VALUE);

        assertEquals("Bearer " + BEARER_TOKEN_VALUE, response.getHeaderString(AUTHORIZATION_HEADER_KEY));
    }

    @Test
    public void whenCallingBearerAuthenticationWithOAuth2AtRequestLevel_thenHeadersReturnedBack() {
        Response response = JerseyClientHeaders.bearerAuthenticationWithOAuth2AtRequestLevel(BEARER_TOKEN_VALUE, BEARER_REQUEST_TOKEN_VALUE);

        assertEquals("Bearer " + BEARER_REQUEST_TOKEN_VALUE, response.getHeaderString(AUTHORIZATION_HEADER_KEY));
    }

    @Test
    public void whenCallingFilter_thenHeadersReturnedBack() {
        Response response = JerseyClientHeaders.filter();

        assertEquals(AddHeaderOnRequestFilter.FILTER_HEADER_VALUE, response.getHeaderString(AddHeaderOnRequestFilter.FILTER_HEADER_KEY));
    }

    @Test
    public void whenCallingSendRestrictedHeaderThroughDefaultTransportConnector_thenHeadersReturnedBack() {
        Response response = JerseyClientHeaders.sendRestrictedHeaderThroughDefaultTransportConnector("keep-alive", "keep-alive-value");

        assertEquals("keep-alive-value", response.getHeaderString("keep-alive"));
    }

    @Test
    public void whenCallingSimpleSSEHeader_thenHeadersReturnedBack() throws InterruptedException {
        String sseHeaderBackValue = JerseyClientHeaders.simpleSSEHeader();

        assertEquals(AddHeaderOnRequestFilter.FILTER_HEADER_VALUE, sseHeaderBackValue);
    }

    private void assertBearerAuthenticationHeaders(Map<String, String> subHeadersMap) {

        assertEquals(BEARER_TOKEN_VALUE, subHeadersMap.get("oauth_token"));
        assertEquals(BEARER_CONSUMER_KEY_VALUE, subHeadersMap.get("oauth_consumer_key"));
        assertNotNull(subHeadersMap.get("oauth_nonce"));
        assertNotNull(subHeadersMap.get("oauth_signature"));
        assertNotNull(subHeadersMap.get("oauth_callback"));
        assertNotNull(subHeadersMap.get("oauth_signature_method"));
        assertNotNull(subHeadersMap.get("oauth_version"));
        assertNotNull(subHeadersMap.get("oauth_timestamp"));
    }

    private void assertDigestAuthenticationHeaders(Map<String, String> subHeadersMap) {
        assertEquals(EchoHeaders.NONCE_VALUE, subHeadersMap.get(EchoHeaders.NONCE_KEY));
        assertEquals(EchoHeaders.OPAQUE_VALUE, subHeadersMap.get(EchoHeaders.OPAQUE_KEY));
        assertEquals(EchoHeaders.QOP_VALUE, subHeadersMap.get(EchoHeaders.QOP_KEY));
        assertEquals(EchoHeaders.REALM_VALUE, subHeadersMap.get(EchoHeaders.REALM_KEY));

        assertEquals(USERNAME, subHeadersMap.get("username"));
        assertEquals("/echo-headers/digest", subHeadersMap.get("uri"));
        assertNotNull(subHeadersMap.get("cnonce"));
        assertEquals("00000001", subHeadersMap.get("nc"));
        assertNotNull(subHeadersMap.get("response"));
    }

    private Map<String, String> parseAuthenticationSubHeader(Response response, int startAt) {
        String authorizationHeader = response.getHeaderString(AUTHORIZATION_HEADER_KEY);
        // The substring(startAt) is used to cut off the authentication schema part from the value returned.
        String[] subHeadersKeyValue = authorizationHeader.substring(startAt).split(",");
        Map<String, String> subHeadersMap = new HashMap<>();

        for (String subHeader : subHeadersKeyValue) {
            String[] keyValue = subHeader.split("=");

            if (keyValue[1].startsWith("\"")) {
                keyValue[1] = keyValue[1].substring(1, keyValue[1].length() - 1);
            }

            subHeadersMap.put(keyValue[0].trim(), keyValue[1].trim());
        }
        return subHeadersMap;
    }

    private void assertBasicAuthenticationHeaders(Response response) {
        String base64Credentials = response.getHeaderString(AUTHORIZATION_HEADER_KEY);
        // The substring(6) is used to cut the "Basic " part of the value returned,
        // as it's used to indicates the authentication schema and does not belong to the credentials
        byte[] credentials = Base64.getDecoder().decode(base64Credentials.substring(6));
        String[] credentialsParsed = new String(credentials).split(":");

        assertEquals(credentialsParsed[0], USERNAME);
        assertEquals(credentialsParsed[1], PASSWORD);
    }

    @Override
    protected Application configure() {
        return new ResourceConfig()
                .register(EchoHeaders.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // We need this definition here, because if you are running
        // the complete suit test the sendingRestrictedHeaderThroughDefaultTransportConnector_shouldReturnThanBack
        // will fail if only defined on the client method, since the JerseyTest is created once.
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    }
}