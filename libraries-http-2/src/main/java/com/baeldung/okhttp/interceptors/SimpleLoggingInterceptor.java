package com.baeldung.okhttp.interceptors;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SimpleLoggingInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleLoggingInterceptor.class);

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        LOGGER.info("Intercepted headers: {} from URL: {}", request.headers(), request.url());

        return chain.proceed(request);
    }

}
