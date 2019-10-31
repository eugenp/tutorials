package com.baeldung.lambda;

import com.amazonaws.services.lambda.runtime.Context;

public class LambdaMethodHandler {
    public String handleRequest(String input, Context context) {
        context.getLogger().log("Input: " + input);
        return "Hello World - " + input;
    }
}
