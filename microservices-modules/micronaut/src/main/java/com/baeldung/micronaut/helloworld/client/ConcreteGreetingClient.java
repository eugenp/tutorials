package com.baeldung.micronaut.helloworld.client;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;

@Singleton
public class ConcreteGreetingClient
{
   private HttpClient httpClient;

   public ConcreteGreetingClient(@Client("/") HttpClient httpClient) {
      this.httpClient = httpClient;
   }

   public String greet(String name) {
      HttpRequest<String> req = HttpRequest.GET("/greet/" + name);
      return Single.fromPublisher(httpClient.retrieve(req)).blockingGet();
   }

   public Single<String> greetAsync(String name) {
      HttpRequest<String> req = HttpRequest.GET("/async/greet/" + name);
      return Single.fromPublisher(httpClient.retrieve(req));
   }
}
