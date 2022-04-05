package com.baeldung.vavr.exception.handling.client;


public interface HttpClient {
    Response call() throws ClientException;
}
