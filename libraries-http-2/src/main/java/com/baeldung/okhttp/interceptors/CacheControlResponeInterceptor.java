package com.baeldung.okhttp.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class CacheControlResponeInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder()
            .header("Cache-Control", "no-store")
            .build();
    }

}
