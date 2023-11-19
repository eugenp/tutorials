package com.baeldung.lambda.todo.service;

import com.baeldung.lambda.todo.config.Config;
import org.junit.Rule;
import org.junit.Test;
import uk.org.webcompere.systemstubs.rules.SystemOutRule;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ToDoReaderServiceUnitTest {

    @Rule
    public SystemOutRule systemOutRule = new SystemOutRule();

    @Test
    public void whenTheServiceStarts_thenItOutputsEndpoint() {
        Config config = new Config();
        config.setToDoEndpoint("https://todo-endpoint.com");
        ToDoReaderService service = new ToDoReaderService(config, null);

        assertThat(systemOutRule.getLinesNormalized())
          .contains("ToDo Endpoint on: https://todo-endpoint.com");
    }
}