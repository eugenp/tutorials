package com.baeldung.cloud.openfeign.client;

import java.net.URI;

import org.springframework.cloud.openfeign.FeignClient;

import com.baeldung.cloud.openfeign.model.Todo;

import feign.RequestLine;

@FeignClient(name = "todoClient")
public interface TodoClient {
    @RequestLine(value = "GET")
    Todo getTodoById(URI uri);
}
