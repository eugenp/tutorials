package org.baeldung.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

/**
 * An example of HttpClient can be customized to authenticate
 * preemptively using DIGEST scheme.
 * <b/>
 * Generally, preemptive authentication can be considered less
 * secure than a response to an authentication challenge
 * and therefore discouraged.
 */
public class ClientPreemptiveDigestAuthentication {

    public static void main(final String[] args) throws Exception {
        final HttpHost targetHost = new HttpHost("localhost", 8080, "http");

        final CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()), new UsernamePasswordCredentials("user1", "user1Pass"));

        final CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
        try {

            // Create AuthCache instance
            final AuthCache authCache = new BasicAuthCache();
            // Generate DIGEST scheme object, initialize it and add it to the local auth cache
            final DigestScheme digestAuth = new DigestScheme();
            // Suppose we already know the realm name
            digestAuth.overrideParamter("realm", "Custom Realm Name");

            // digestAuth.overrideParamter("nonce", "whatever");
            authCache.put(targetHost, digestAuth);

            // Add AuthCache to the execution context
            final BasicHttpContext localcontext = new BasicHttpContext();
            localcontext.setAttribute(HttpClientContext.AUTH_CACHE, authCache);

            final HttpGet httpget = new HttpGet("http://localhost:8080/spring-security-rest-digest-auth/api/foos/1");

            System.out.println("executing request: " + httpget.getRequestLine());
            System.out.println("to target: " + targetHost);

            for (int i = 0; i < 3; i++) {
                final HttpResponse response = httpclient.execute(targetHost, httpget, localcontext);
                final HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                }
                EntityUtils.consume(entity);
            }
        } finally {
            // When HttpClient instance is no longer needed, shut down the connection manager to ensure immediate deallocation of all system resources
            httpclient.close();
        }
    }

}
