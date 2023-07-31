package com.baeldung.cloud.openfeign.config;

import java.util.function.Supplier;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class DynamicUrlInterceptor implements RequestInterceptor {

    private final Supplier<String> urlSupplier;

    public DynamicUrlInterceptor(Supplier<String> urlSupplier) {
        this.urlSupplier = urlSupplier;
    }

    @Override
    public void apply(RequestTemplate template) {
        String url = urlSupplier.get();
        if (url != null) {
            template.target(url);
        }
    }
}
