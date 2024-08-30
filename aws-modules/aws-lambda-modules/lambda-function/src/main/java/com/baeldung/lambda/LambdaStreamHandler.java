package com.baeldung.lambda;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LambdaStreamHandler implements RequestStreamHandler {

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Request request = mapper.readValue(input, Request.class);

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output))) {
            writer.write("Hello " + request.name() + ", Baeldung has great Java content for you!");
            writer.flush();
        }
    }

    record Request(String name) {}

}