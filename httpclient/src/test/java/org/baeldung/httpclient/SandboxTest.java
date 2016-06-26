package org.baeldung.httpclient;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.junit.Ignore;
import org.junit.Test;

public class SandboxTest {

    // original example
    @Ignore
    @Test
    public final void whenInterestingDigestAuthScenario_then401UnAuthorized() throws AuthenticationException, ClientProtocolException, IOException, MalformedChallengeException {
        final HttpHost targetHost = new HttpHost("httpbin.org", 80, "http");

        // set up the credentials to run agains the server
        final CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()), new UsernamePasswordCredentials("user", "passwd"));

        // We need a first run to get a 401 to seed the digest auth

        // Make a client using those creds
        final CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

        // And make a call to the URL we are after
        final HttpGet httpget = new HttpGet("http://httpbin.org/digest-auth/auth/user/passwd");

        // Create a context to use
        final HttpClientContext context = HttpClientContext.create();

        // Get a response from the sever (expect a 401!)
        final HttpResponse authResponse = client.execute(targetHost, httpget, context);

        // Pull out the auth header that came back from the server
        final Header challenge = authResponse.getHeaders("WWW-Authenticate")[0];

        // Lets use a digest scheme to solve it
        final DigestScheme digest = new DigestScheme();
        digest.processChallenge(challenge);

        // Make a header with the solution based upon user/password and what the digest got out of the initial 401 reponse
        final Header solution = digest.authenticate(new UsernamePasswordCredentials("user", "passwd"), httpget, context);

        // Need an auth cache to use the new digest we made
        final AuthCache authCache = new BasicAuthCache();
        authCache.put(targetHost, digest);

        // Add the authCache and thus solved digest to the context
        context.setAuthCache(authCache);

        // Pimp up our http get with the solved header made by the digest
        httpget.addHeader(solution);

        // use it!
        System.out.println("Executing request " + httpget.getRequestLine() + " to target " + targetHost);

        for (int i = 0; i < 3; i++) {
            final CloseableHttpResponse responseGood = client.execute(targetHost, httpget, context);

            try {
                System.out.println("----------------------------------------");
                System.out.println(responseGood.getStatusLine());
                System.out.println(EntityUtils.toString(responseGood.getEntity()));
            } finally {
                responseGood.close();
            }
        }
    }

    @Test
    public final void whenInterestingDigestAuthScenario_then200OK() throws AuthenticationException, ClientProtocolException, IOException, MalformedChallengeException {
        final HttpHost targetHost = new HttpHost("httpbin.org", 80, "http");

        // set up the credentials to run agains the server
        final CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()), new UsernamePasswordCredentials("user", "passwd"));

        // This endpoint need fake cookie to work properly
        final CookieStore cookieStore = new BasicCookieStore();
        final BasicClientCookie cookie = new BasicClientCookie("fake", "fake_value");
        cookie.setDomain("httpbin.org");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);

        // Make a client using those creds
        final CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).setDefaultCredentialsProvider(credsProvider).build();
        
        // And make a call to the URL we are after
        final HttpGet httpget = new HttpGet("http://httpbin.org/digest-auth/auth/user/passwd");

        // Create a context to use
        final HttpClientContext context = HttpClientContext.create();

        // Get a response from the sever (401 implicitly)
        final HttpResponse authResponse = client.execute(targetHost, httpget, context);
        assertEquals(200, authResponse.getStatusLine().getStatusCode());

        // HttpClient will use cached digest parameters for future requests
        System.out.println("Executing request " + httpget.getRequestLine() + " to target " + targetHost);

        for (int i = 0; i < 3; i++) {
            final CloseableHttpResponse responseGood = client.execute(targetHost, httpget, context);

            try {
                System.out.println("----------------------------------------");
                System.out.println(responseGood.getStatusLine());
                assertEquals(200, responseGood.getStatusLine().getStatusCode());
            } finally {
                responseGood.close();
            }
        }
        client.close();
    }

    // This test needs module spring-security-rest-digest-auth to be running
    @Test
    public final void whenWeKnowDigestParameters_thenNo401Status() throws AuthenticationException, ClientProtocolException, IOException, MalformedChallengeException {
        final HttpHost targetHost = new HttpHost("localhost", 8080, "http");

        final CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("user1", "user1Pass"));

        final CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

        final HttpGet httpget = new HttpGet("http://localhost:8080/spring-security-rest-digest-auth/api/foos/1");

        final HttpClientContext context = HttpClientContext.create();
        // == make it preemptive
        final AuthCache authCache = new BasicAuthCache();
        final DigestScheme digestAuth = new DigestScheme();
        digestAuth.overrideParamter("realm", "Custom Realm Name");
        digestAuth.overrideParamter("nonce", "nonce value goes here");
        authCache.put(targetHost, digestAuth);
        context.setAuthCache(authCache);
        // == end
        System.out.println("Executing The Request knowing the digest parameters ==== ");
        final HttpResponse authResponse = client.execute(targetHost, httpget, context);
        assertEquals(200, authResponse.getStatusLine().getStatusCode());
        client.close();
    }

    // This test needs module spring-security-rest-digest-auth to be running
    @Test
    public final void whenDoNotKnowParameters_thenOnlyOne401() throws AuthenticationException, ClientProtocolException, IOException, MalformedChallengeException {
        final HttpClientContext context = HttpClientContext.create();
        final HttpHost targetHost = new HttpHost("localhost", 8080, "http");
        final CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("user1", "user1Pass"));
        final CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

        final HttpGet httpget = new HttpGet("http://localhost:8080/spring-security-rest-digest-auth/api/foos/1");
        System.out.println("Executing The Request NOT knowing the digest parameters ==== ");
        final HttpResponse tempResponse = client.execute(targetHost, httpget, context);
        assertEquals(200, tempResponse.getStatusLine().getStatusCode());

        for (int i = 0; i < 3; i++) {
            System.out.println("No more Challenges or 401");
            final CloseableHttpResponse authResponse = client.execute(targetHost, httpget, context);
            assertEquals(200, authResponse.getStatusLine().getStatusCode());
            authResponse.close();
        }
        client.close();
    }
}
