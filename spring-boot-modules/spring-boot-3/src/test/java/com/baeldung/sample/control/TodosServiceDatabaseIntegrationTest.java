package com.baeldung.sample.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@TestPropertySource(
  properties = {
    "application.data.initialize-on-startup=false"
  }
)
class TodosServiceDatabaseIntegrationTest {

    @Autowired
    TodosService service;

    @BeforeEach
    void assertEmpty() {
        assertThat(service.count())
          .isZero();
    }

    @Test
    void testCreate() {
        service.create(new Todo(null, "test"));
        assertThat(service.count())
          .isEqualTo(1);
        assertThat(service.findAll())
          .hasSize(1)
          .element(0).extracting(Todo::title).isEqualTo("test");
    }

    @Test
    void testFindById() {
        final var todo = service.create(new Todo(null, "test"));
        final var result = service.findById(todo.id());
        assertThat(result)
          .isNotEmpty()
          .get().usingRecursiveComparison().isEqualTo(todo);
    }

    @Test
    void testDelete() {
        final var todo = service.create(new Todo(null, "test"));
        final var id = todo.id();
        service.delete(id);
        final var result = service.findById(id);
        assertThat(result).isEmpty();
        assertThatThrownBy(() -> service.delete(id))
          .isInstanceOf(NotFoundException.class);
    }

}
