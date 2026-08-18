package com.baeldung.springai.todowritetool;

import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.springai.todowritetool.config.TodoAgentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class TodoAgentServiceIntegrationTest {

    @Autowired
    private TodoAgentService todoAgentService;

    @Autowired
    private TodoService todoService;

    @Test
    void whenUserAsksToTrackSteps_thenTodoListIsPopulated() {
        String response = todoAgentService.ask(
            "Track these steps as a todo list: set up the project, "
                + "write the tool, add tests");

        assertThat(response).isNotBlank();
        assertThat(todoService.read()).isNotEmpty();
    }
}
