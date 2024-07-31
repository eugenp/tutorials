package com.baeldung.boot.bootstrapmode;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.config.BootstrapMode;

import com.baeldung.boot.bootstrapmode.domain.Todo;
import com.baeldung.boot.bootstrapmode.repository.TodoRepository;

@DataJpaTest(bootstrapMode = BootstrapMode.LAZY)
class BootstrapmodeLazyIntegrationTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void givenBootstrapmodeValueIsLazy_whenCreatingTodo_shouldSuccess() {
        Todo todo = new Todo("Something to be done");

        assertThat(todoRepository.save(todo)).hasNoNullFieldsOrProperties();
    }
}
