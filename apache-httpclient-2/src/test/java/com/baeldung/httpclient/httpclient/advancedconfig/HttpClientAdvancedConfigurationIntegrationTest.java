package com.baeldung.httpclient.advancedconfig;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.hc.client5.http.auth.AuthCache;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.CredentialsProvider;
import org.apache.hc.client5.http.auth.StandardAuthScheme;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.auth.BasicAuthCache;
import org.apache.hc.client5.http.impl.auth.BasicScheme;
import org.apache.hc.client5.http.impl.auth.CredentialsProviderBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.routing.DefaultProxyRoutePlanner;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class HttpClientAdvancedConfigurationIntegrationTest {

    @Rule
    public WireMockRule serviceMock = new WireMockRule(8089);

    @Rule
    public WireMockRule proxyMock = new WireMockRule(8090);

    @Test
    public void givenClientWithCustomUserAgentHeader_whenExecuteRequest_shouldReturn200() throws IOException {
        //given
        String userAgent = "BaeldungAgent/1.0";
        serviceMock.stubFor(get(urlEqualTo("/detail"))
          .withHeader("User-Agent", equalTo(userAgent))
          .willReturn(aResponse()
            .withStatus(200)));

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8089/detail");
        httpGet.setHeader(HttpHeaders.USER_AGENT, userAgent);

        //when
        HttpResponse response = httpClient.execute(httpGet);

        //then
        assertEquals(response.getCode(), 200);
    }

    @Test
    public void givenClientThatSendDataInBody_whenSendXmlInBody_shouldReturn200() throws IOException {
        //given
        String xmlBody = "<xml><id>1</id></xml>";
        serviceMock.stubFor(post(urlEqualTo("/person"))
          .withHeader("Content-Type", equalTo("application/xml"))
          .withRequestBody(equalTo(xmlBody))
          .willReturn(aResponse()
            .withStatus(200)));

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8089/person");
        httpPost.setHeader("Content-Type", "application/xml");
        StringEntity xmlEntity = new StringEntity(xmlBody);
        httpPost.setEntity(xmlEntity);

        //when
        HttpResponse response = httpClient.execute(httpPost);

        //then
        assertEquals(response.getCode(), 200);

    }

    @Test
    public void givenServerThatIsBehindProxy_whenClientIsConfiguredToSendRequestViaProxy_shouldReturn200() throws IOException {
        //given
        proxyMock.stubFor(get(urlMatching(".*"))
          .willReturn(aResponse().proxiedFrom("http://localhost:8089/")));

        serviceMock.stubFor(get(urlEqualTo("/private"))
          .willReturn(aResponse().withStatus(200)));


        HttpHost proxy = new HttpHost("localhost", 8090);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        HttpClient httpclient = HttpClients.custom()
          .setRoutePlanner(routePlanner)
          .build();

        //when
        final HttpGet httpGet = new HttpGet("http://localhost:8089/private");
        HttpResponse response = httpclient.execute(httpGet);

        //then
        assertEquals(response.getCode(), 200);
        proxyMock.verify(getRequestedFor(urlEqualTo("/private")));
        serviceMock.verify(getRequestedFor(urlEqualTo("/private")));
    }

    @Test
    public void givenServerThatIsBehindAuthorizationProxy_whenClientSendRequest_shouldAuthorizeProperly() throws IOException {
        //given
        proxyMock.stubFor(get(urlMatching("/private"))
          .willReturn(aResponse().proxiedFrom("http://localhost:8089/")));
        serviceMock.stubFor(get(urlEqualTo("/private"))
          .willReturn(aResponse().withStatus(200)));


        HttpHost proxy = new HttpHost("localhost", 8090);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

        // Client credentials
        CredentialsProvider credentialsProvider = CredentialsProviderBuilder.create()
            .add(new AuthScope(proxy), "username_admin", "secret_password".toCharArray())
            .build();

        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(proxy, basicAuth);
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credentialsProvider);
        context.setAuthCache(authCache);


        HttpClient httpclient = HttpClients.custom()
          .setRoutePlanner(routePlanner)
          .setDefaultCredentialsProvider(credentialsProvider)
          .build();


        //when
        final HttpGet httpGet = new HttpGet("http://localhost:8089/private");
        httpGet.setHeader("Authorization", StandardAuthScheme.BASIC);
        HttpResponse response = httpclient.execute(httpGet, context);

        //then
        assertEquals(response.getCode(), 200);
        proxyMock.verify(getRequestedFor(urlEqualTo("/private")).withHeader("Authorization", containing("Basic")));
        serviceMock.verify(getRequestedFor(urlEqualTo("/private")));
    }


}
