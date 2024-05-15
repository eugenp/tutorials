package com.baeldung.resttemplate.proxy;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * This class is used to test a request using {@link RestTemplate} with {@link Proxy}
 * using a {@link SimpleClientHttpRequestFactory} as configuration. 
 * <br />
 * <br />
 *
 * Before running the test we should change the <code>PROXY_SERVER_HOST</code> 
 * and <code>PROXY_SERVER_PORT</code> constants in our class to match our preferred proxy configuration.
 */
public class RequestFactoryLiveTest {

    private static final String PROXY_SERVER_HOST = "127.0.0.1";
    private static final int PROXY_SERVER_PORT = 8080;

    RestTemplate restTemplate;

    @Before
    public void setUp() {
        Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(PROXY_SERVER_HOST, PROXY_SERVER_PORT));

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);

        restTemplate = new RestTemplate(requestFactory);
    }

    @Test
    public void givenRestTemplate_whenRequestedWithProxy_thenResponseBodyIsOk() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://httpbin.org/get", String.class);

        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }
}
