package com.baeldung.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.Collections;

/**
 * @author aiet
 */
public class HystrixSyncHttpCommand extends HystrixCommand<String> {

    private URI uri;
    private RequestConfig requestConfig;

    HystrixSyncHttpCommand(URI uri, int timeoutMillis) {
        super(Setter
          .withGroupKey(HystrixCommandGroupKey.Factory.asKey("hystrix-ratpack-sync"))
          .andCommandPropertiesDefaults(HystrixCommandProperties
            .Setter()
            .withExecutionTimeoutInMilliseconds(timeoutMillis)));
        requestConfig = RequestConfig
          .custom()
          .setSocketTimeout(timeoutMillis)
          .setConnectTimeout(timeoutMillis)
          .setConnectionRequestTimeout(timeoutMillis)
          .build();
        this.uri = uri;
    }

    @Override
    protected String run() throws Exception {
        HttpGet request = new HttpGet(uri);
        return EntityUtils.toString(HttpClientBuilder
          .create()
          .setDefaultRequestConfig(requestConfig)
          .setDefaultHeaders(Collections.singleton(new BasicHeader("User-Agent", "Baeldung Blocking HttpClient")))
          .build()
          .execute(request)
          .getEntity());
    }

    @Override
    protected String getFallback() {
        return "eugenp's sync fallback profile";
    }

}
