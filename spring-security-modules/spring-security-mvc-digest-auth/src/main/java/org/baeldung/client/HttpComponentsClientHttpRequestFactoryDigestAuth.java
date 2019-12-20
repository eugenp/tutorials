package org.baeldung.client;

import org.apache.http.HttpHost;
import org.apache.http.client.AuthCache;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;

public class HttpComponentsClientHttpRequestFactoryDigestAuth extends HttpComponentsClientHttpRequestFactory {
    HttpHost host;

    public HttpComponentsClientHttpRequestFactoryDigestAuth(final HttpHost host, final HttpClient httpClient) {
        super(httpClient);
        this.host = host;
    }

    //

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
        digestAuth.overrideParamter("realm", "Custom Realm Name");

        // digestAuth.overrideParamter("nonce", "MTM3NTU2OTU4MDAwNzoyYWI5YTQ5MTlhNzc5N2UxMGM5M2Y5M2ViOTc4ZmVhNg==");
        authCache.put(host, digestAuth);

        // Add AuthCache to the execution context
        final BasicHttpContext localcontext = new BasicHttpContext();
        localcontext.setAttribute(HttpClientContext.AUTH_CACHE, authCache);
        return localcontext;
    }

}