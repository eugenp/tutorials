package com.baeldung.spring.cloudfunction.functions.aws;

import java.util.function.Function;

public class Greeter implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "Hello " + s + ", and welcome to Spring Cloud Function!!!";
    }
}
