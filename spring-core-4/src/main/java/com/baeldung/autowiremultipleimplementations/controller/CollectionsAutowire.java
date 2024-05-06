package com.baeldung.autowiremultipleimplementations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.autowiremultipleimplementations.service.GoodService;

import java.util.Map;
import java.util.Set;


@Component
public class CollectionsAutowire {

    private final Set<GoodService> goodServices;
    private final Map<String, GoodService> goodServiceMap;

    @Autowired
    public CollectionsAutowire(Set<GoodService> goodServices, Map<String, GoodService> goodServiceMap) {
        this.goodServices = goodServices;
        this.goodServiceMap = goodServiceMap;
    }

    public String hello() {
        return goodServices.stream()
                .map(GoodService::getHelloMessage)
                .reduce((a, b) -> a + " " + b)
                .orElse("No services available");
    }

    public String helloMap() {
        return goodServiceMap.values().stream()
                .map(GoodService::getHelloMessage)
                .reduce((a, b) -> a + " " + b)
                .orElse("No services available");
    }
}
