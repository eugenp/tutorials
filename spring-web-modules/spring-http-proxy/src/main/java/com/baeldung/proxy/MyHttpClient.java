package com.baeldung.proxy;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.bind.annotation.RequestBody;

@HttpExchange(url = "/greet")
public interface MyHttpClient {

    @GetExchange
    String greet();

    @PostExchange
    String greet(@RequestBody String username);
}