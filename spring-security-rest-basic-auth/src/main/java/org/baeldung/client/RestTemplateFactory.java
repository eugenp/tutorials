package org.baeldung.client;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean {
    private RestTemplate restTemplate;

    public RestTemplateFactory() {
        super();
    }

    // API

    @Override
    public RestTemplate getObject() {
        return restTemplate;
    }

    @Override
    public Class<RestTemplate> getObjectType() {
        return RestTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() {
        final HttpHost host = new HttpHost("localhost", 8080, "http");
        final HttpComponentsClientHttpRequestFactoryBasicAuth requestFactory = new HttpComponentsClientHttpRequestFactoryBasicAuth(host);
        restTemplate = new RestTemplate(requestFactory);

        final int timeout = 5;
        final HttpClient httpClient = requestFactory.getHttpClient();
        // - note: timeout via raw String parameters
        // httpClient.getParams().setParameter("http.connection.timeout", timeout * 1000);
        // httpClient.getParams().setParameter("http.socket.timeout", timeout * 1000);

        // httpClient.getParams().setParameter("http.connection-manager.timeout", new Long(timeout * 1000));
        // httpClient.getParams().setParameter("http.protocol.head-body-timeout", timeout * 1000);

        // - note: timeout via the API
        final HttpParams httpParams = httpClient.getParams();
        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);
        httpParams.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT, new Long(timeout * 1000));

        HttpConnectionParams.setConnectionTimeout(httpParams, timeout * 1000); // http.connection.timeout
        HttpConnectionParams.setSoTimeout(httpParams, timeout * 1000); // http.socket.timeout
    }

}