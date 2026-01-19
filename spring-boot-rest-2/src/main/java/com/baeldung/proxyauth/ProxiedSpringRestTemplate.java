package com.baeldung.proxyauth;

import java.net.Authenticator;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ProxiedSpringRestTemplate {

    private ProxiedSpringRestTemplate() {
    }

    public static RestTemplate createClient(ProxyConfig config) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(config.proxy());

        Authenticator.setDefault(config.authenticator());

        return new RestTemplate(requestFactory);
    }

    public static String sendRequest(RestTemplate restTemplate, String url) {
        return restTemplate.getForObject(url, String.class);
    }
}
