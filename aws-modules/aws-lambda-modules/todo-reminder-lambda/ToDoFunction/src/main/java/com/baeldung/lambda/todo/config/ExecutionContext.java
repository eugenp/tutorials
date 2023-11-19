package com.baeldung.lambda.todo.config;

import com.baeldung.lambda.todo.service.PostService;
import com.baeldung.lambda.todo.service.ToDoReaderService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutionContext {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ExecutionContext.class);

    private ToDoReaderService toDoReaderService;
    private PostService postService;

    public ExecutionContext() {
        LOGGER.info("Loading configuration");

        try {
            Injector injector = Guice.createInjector(new Services());
            this.toDoReaderService = injector.getInstance(ToDoReaderService.class);
            this.postService = injector.getInstance(PostService.class);
        } catch (Exception e) {
            LOGGER.error("Could not start", e);
        }
    }

    public ToDoReaderService getToDoReaderService() {
        return toDoReaderService;
    }

    public PostService getPostService() {
        return postService;
    }
}
