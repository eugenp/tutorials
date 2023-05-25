package com.baeldung.soap.ws.server;

import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CountryServicePublisher {
    
    private static final Logger logger = LoggerFactory.getLogger(CountryServicePublisher.class);
    
    public static void main(String[] args) {
        Endpoint endpoint = Endpoint.create(new CountryServiceImpl());
        endpoint.publish("http://localhost:8888/ws/country");
        
        logger.info("Country web service ready to consume requests!");
    }
}