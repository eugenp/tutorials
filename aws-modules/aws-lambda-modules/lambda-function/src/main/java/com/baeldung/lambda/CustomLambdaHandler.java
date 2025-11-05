package com.baeldung.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.logging.LogLevel;

public class CustomLambdaHandler {

    public Response handlingRequestFreely(Request request, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log(request.name() + " has invoked the lambda function", LogLevel.INFO);
        return new Response("Subscribe to Baeldung Pro: baeldung.com/members");
    }

    record Request(String name) {}

    record Response(String answer) {}

}