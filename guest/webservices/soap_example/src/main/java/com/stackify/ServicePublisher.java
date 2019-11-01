package com.stackify;

import javax.xml.ws.Endpoint;

import com.stackify.services.DefaultUserImpl;

public class ServicePublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/users", new DefaultUserImpl());
    }
}
