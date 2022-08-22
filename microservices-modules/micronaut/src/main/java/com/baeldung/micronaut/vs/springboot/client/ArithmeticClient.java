package com.baeldung.micronaut.vs.springboot.client;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("/math")
public interface ArithmeticClient {

    @Get("/sum/{number1}/{number2}")
    String sum(float number1, float number2);

    @Get("/subtract/{number1}/{number2}")
    String subtract(float number1, float number2);

    @Get("/multiply/{number1}/{number2}")
    String multiply(float number1, float number2);

    @Get("/divide/{number1}/{number2}")
    String divide(float number1, float number2);

    @Get("/memory")
    String memory();
}
