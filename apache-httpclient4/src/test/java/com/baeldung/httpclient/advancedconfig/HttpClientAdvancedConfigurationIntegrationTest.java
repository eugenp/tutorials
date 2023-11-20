package com.baeldung.httpclient.advancedconfig;

import com.github.tomakehurst.wiremock.WireMockServer;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpClientAdvancedConfigurationIntegrationTest {

    public WireMockServer serviceMock;
    public WireMockServer proxyMock;

    @BeforeEach
    public void before () {
        serviceMock = new WireMockServer(8089);
        serviceMock.start();
        proxyMock = new WireMockServer(8090);
        proxyMock.start();
    }

    @AfterEach
    public void after () {
        serviceMock.stop();
        proxyMock.stop();
    }

    @Test
    void givenClientWithCustomUserAgentHeader_whenExecuteRequest_shouldReturn200() throws IOException {
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
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    void givenClientThatSendDataInBody_whenSendXmlInBody_shouldReturn200() throws IOException {
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
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    void givenServerThatIsBehindProxy_whenClientIsConfiguredToSendRequestViaProxy_shouldReturn200() throws IOException {
        //given
        proxyMock.stubFor(get(urlMatching(".*"))
          .willReturn(aResponse().proxiedFrom("http://localhost:8089")));

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
        assertEquals(200, response.getStatusLine().getStatusCode());
        proxyMock.verify(getRequestedFor(urlEqualTo("/private")));
        serviceMock.verify(getRequestedFor(urlEqualTo("/private")));
    }

    @Test
    void givenServerThatIsBehindAuthorizationProxy_whenClientSendRequest_shouldAuthorizeProperly() throws IOException {
        //given
        proxyMock.stubFor(get(urlMatching("/private"))
          .willReturn(aResponse().proxiedFrom("http://localhost:8089")));
        serviceMock.stubFor(get(urlEqualTo("/private"))
          .willReturn(aResponse().withStatus(200)));


        HttpHost proxy = new HttpHost("localhost", 8090);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

        // Client credentials
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(proxy),
          new UsernamePasswordCredentials("username_admin", "secret_password"));


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
        HttpResponse response = httpclient.execute(httpGet, context);

        //then
        assertEquals(200, response.getStatusLine().getStatusCode());
        proxyMock.verify(getRequestedFor(urlEqualTo("/private")).withHeader("Authorization", containing("Basic")));
        serviceMock.verify(getRequestedFor(urlEqualTo("/private")));
    }


}
