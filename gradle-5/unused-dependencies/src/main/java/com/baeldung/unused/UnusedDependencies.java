package com.baeldung.unused;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpHeaders;
import org.apache.http.ssl.SSLContextBuilder;

import com.google.common.collect.ImmutableList;

public class UnusedDependencies {

    public static void main(String[] args) {
        System.out.println("Hello world");
        useGuava();
        useHttpCore();
        useHttpClientWithReflection();
    }

    private static void useGuava() {
        List<String> list = ImmutableList.of("Baledung", "is", "cool");
        System.out.println(list.stream()
            .collect(Collectors.joining(" ")));
    }

    private static void useHttpCore() {
        SSLContextBuilder.create();
        // does not trigger the direct dep violation
        System.out.println(HttpHeaders.ACCEPT);
    }

    private static void useHttpClientWithReflection() {
        try {
            Class<?> httpBuilder = Class.forName("org.apache.http.impl.client.HttpClientBuilder");
            Method create = httpBuilder.getMethod("create", null);
            create.invoke(httpBuilder, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
