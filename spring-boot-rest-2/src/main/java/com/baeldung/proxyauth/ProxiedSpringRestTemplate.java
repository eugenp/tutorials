package com.baeldung.proxyauth;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ProxiedSpringRestTemplate {

    private ProxiedSpringRestTemplate() {
    }

    public static RestTemplate createClient(ProxyConfig config) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(config.getHost(), config.getPort()));
        requestFactory.setProxy(proxy);

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUsername(), config.getPassword()
                    .toCharArray());
            }
        });

        return new RestTemplate(requestFactory);
    }

    public static String sendRequest(RestTemplate restTemplate, String url) {
        return restTemplate.getForObject(url, String.class);
    }
}
