package com.baeldung.spring.cloudfunction.functions;

import java.util.function.Function;

public class Greeter implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "Hello " + s + ", Welcome to Spring Cloud functions!!!";
    }
}
