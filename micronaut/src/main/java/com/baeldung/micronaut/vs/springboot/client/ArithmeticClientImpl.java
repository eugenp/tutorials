package com.baeldung.micronaut.vs.springboot.client;

import javax.inject.Singleton;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;

@Singleton
public class ArithmeticClientImpl {
	private RxHttpClient httpClient;

	public ArithmeticClientImpl(@Client("/") RxHttpClient httpClient) {
	    this.httpClient = httpClient;
    }

    public String sum(float number1, float number2) {
	    HttpRequest<String> req = HttpRequest.GET("/math/sum/" + number1 + "/" + number2);
	    return httpClient.retrieve(req).blockingFirst();
	}
    
    public String subtract(float number1, float number2) {
	    HttpRequest<String> req = HttpRequest.GET("/math/subtract/" + number1 + "/" + number2);
	    return httpClient.retrieve(req).blockingFirst();
	}
    
    public String multiply(float number1, float number2) {
	    HttpRequest<String> req = HttpRequest.GET("/math/multiply/" + number1 + "/" + number2);
	    return httpClient.retrieve(req).blockingFirst();
	}
    
    public String divide(float number1, float number2) {
	    HttpRequest<String> req = HttpRequest.GET("/math/divide/" + number1 + "/" + number2);
	    return httpClient.retrieve(req).blockingFirst();
	}
    
    public String memory() {
	    HttpRequest<String> req = HttpRequest.GET("/math/memory");
	    return httpClient.retrieve(req).blockingFirst();
	}
}
