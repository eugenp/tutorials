package com.baeldung.javaslang.exception.handling.client;


public interface HttpClient {
    Response call() throws ClientException;
}
