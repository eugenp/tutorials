package com.baeldung.client;

import org.apache.hc.client5.http.auth.AuthCache;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.auth.BasicAuthCache;
import org.apache.hc.client5.http.impl.auth.DigestScheme;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.protocol.BasicHttpContext;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;

public class HttpComponentsClientHttpRequestFactoryDigestAuth extends HttpComponentsClientHttpRequestFactory {
    HttpHost host;

    public HttpComponentsClientHttpRequestFactoryDigestAuth(final HttpHost host, final HttpClient httpClient) {
        super(httpClient);
        this.host = host;
    }

    @Override
    protected HttpContext createHttpContext(final HttpMethod httpMethod, final URI uri) {
        return createHttpContext();
    }

    private HttpContext createHttpContext() {
        // Create AuthCache instance
        final AuthCache authCache = new BasicAuthCache();
        // Generate DIGEST scheme object, initialize it and add it to the local auth cache
        final DigestScheme digestAuth = new DigestScheme();
        // If we already know the realm name
        digestAuth.initPreemptive(new UsernamePasswordCredentials("user1", "user1Pass".toCharArray()),
                "", "Custom Realm Name");

        // digestAuth.overrideParamter("nonce", "MTM3NTU2OTU4MDAwNzoyYWI5YTQ5MTlhNzc5N2UxMGM5M2Y5M2ViOTc4ZmVhNg==");
        authCache.put(host, digestAuth);

        // Add AuthCache to the execution context
        final BasicHttpContext localcontext = new BasicHttpContext();
        localcontext.setAttribute(HttpClientContext.AUTH_CACHE, authCache);
        return localcontext;
    }

}