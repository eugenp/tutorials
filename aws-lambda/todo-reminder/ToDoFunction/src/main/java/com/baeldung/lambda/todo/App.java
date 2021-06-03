package com.baeldung.lambda.todo;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.baeldung.lambda.todo.config.ExecutionContext;
import com.baeldung.lambda.todo.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestStreamHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private String environmentName = System.getenv("ENV_NAME");
    private ExecutionContext executionContext = new ExecutionContext();

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        context.getLogger().log("App starting\n");
        context.getLogger().log("Environment: "
          + environmentName + "\n");

        try {
            PostService postService = executionContext.getPostService();
            executionContext.getToDoReaderService()
              .getOldestToDo()
              .ifPresent(postService::makePost);
        } catch (Exception e) {
            LOGGER.error("Failed: {}", e.getMessage(), e);
        }
    }
}
