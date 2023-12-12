package com.baeldung.micronaut.vs.springboot.client;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;

@Singleton
public class ArithmeticClientImpl {
    private HttpClient httpClient;

    public ArithmeticClientImpl(@Client("/") HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String sum(float number1, float number2) {
        HttpRequest<String> req = HttpRequest.GET("/math/sum/" + number1 + "/" + number2);
        return Single.fromPublisher(httpClient.retrieve(req))
            .blockingGet();
    }

    public String subtract(float number1, float number2) {
        HttpRequest<String> req = HttpRequest.GET("/math/subtract/" + number1 + "/" + number2);
        return Single.fromPublisher(httpClient.retrieve(req))
            .blockingGet();
    }

    public String multiply(float number1, float number2) {
        HttpRequest<String> req = HttpRequest.GET("/math/multiply/" + number1 + "/" + number2);
        return Single.fromPublisher(httpClient.retrieve(req))
                .blockingGet();
    }

    public String divide(float number1, float number2) {
        HttpRequest<String> req = HttpRequest.GET("/math/divide/" + number1 + "/" + number2);
        return Single.fromPublisher(httpClient.retrieve(req))
                .blockingGet();
    }

    public String memory() {
        HttpRequest<String> req = HttpRequest.GET("/math/memory");
        return Single.fromPublisher(httpClient.retrieve(req))
                .blockingGet();
    }
}
