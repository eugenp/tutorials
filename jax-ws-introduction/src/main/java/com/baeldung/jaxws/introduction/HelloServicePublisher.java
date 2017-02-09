package com.baeldung.jaxws.introduction;

import javax.xml.ws.Endpoint;

/**
 * Created by dreamAdmin on 08/02/2017.
 */
public class HelloServicePublisher {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/helloservice", new HelloServiceImpl());
    }
}
