package com.baeldung.features;

interface HelloWorld {
    default String hello() {
        return "world";
    }
}
