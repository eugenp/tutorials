package com.baeldung.sample.control;

import com.baeldung.sample.entity.TodoEntity;
import com.baeldung.sample.entity.TodosRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Integrationstests des TodosServices. Die Anwendung wird als Black Box behandelt.
 * Somit müssen alle Tests, die einen existierenden Datensatz benötigen, diesen im Test anlegen.
 */
@SpringBootTest(classes = TodosControlLayer.class)
class TodosServiceIntegrationTest {

    // dieses Objekt wird als Mock instruiert
    // Answers.RETURNS_MOCKS ist notwendig, da Methoden in Service-Init-Methode bereits aufgerufen werden
    @MockBean(answer = Answers.RETURNS_MOCKS)
    TodosRepository repo;

    @Autowired
    TodosService service;

    /*
     * Testfall:
     *  - alle Todos auslesen -> kein Fehler
     */
    @Test
    void testFindAllTodos() {
        when(repo.findAll()).thenReturn(List.of(new TodoEntity(1L, "test")));
        final var result = service.findAll();
        assertThat(result).hasSize(1) //
          .element(0).extracting(Todo::id, Todo::title).containsExactly(1L, "test");
    }

    /*
     * Testfall:
     *  - Anlegen eines Todos -> ID besetzt
     *  - Auslesen -> gefunden mit entsprechenden Werten
     */
    @Test
    @SuppressWarnings("ConstantConditions")
    void testCreateTodo() {
        final var newTodo = new Todo(null, "test-todo");
        when(repo.save(any())).thenReturn(new TodoEntity(5L, "test-todo"));
        // create
        final var result = this.service.create(newTodo);
        // find out id
        assertThat(result).extracting(Todo::id).isEqualTo(5L);
        verify(repo).save(refEq(new TodoEntity(null, "test-todo")));
    }

    /*
     * Testfall:
     *  - Ändern eines bestehenden Todos
     *  - Aufruf der Repo-Methode und Rückgabewert prüfen
     */
    @Test
    @SuppressWarnings("ConstantConditions")
    void testUpdateExisting() {
        final var todo = new Todo(5L, "test-todo");
        when(repo.existsById(todo.id())).thenReturn(true);
        // Test
        this.service.update(todo);
        // Assert
        verify(repo).save(refEq(new TodoEntity(5L, "test-todo")));
    }

    /*
     * Testfall:
     *  - Ändern eines nicht existenten Todos
     *  - Aufruf der Repo-Methode und Rückgabewert prüfen
     */
    @Test
    void testUpdateNotExisting() {
        final var todo = new Todo(5L, "test-todo");
        when(repo.existsById(todo.id())).thenReturn(false);
        // Test+Assert
        assertThatThrownBy(() -> this.service.update(todo))
          .isInstanceOf(NotFoundException.class);
        verify(repo).existsById(todo.id());
        verifyNoMoreInteractions(repo);
    }

    /*
     * Testfall:
     *  - Löschen eines existenten Todos
     *  - Aufruf der Repo-Methode und Rückgabewert prüfen
     */
    @Test
    void testDeleteExisting() {
        when(repo.existsById(5L)).thenReturn(true);
        // Test
        this.service.delete(5L);
        // Assert
        verify(repo).deleteById(5L);
    }

    /*
     * Testfall:
     *  - Löschen eines nicht existenten Todos
     *  - Aufruf der Repo-Methode und Rückgabewert prüfen
     */
    @Test
    void testDeleteNotExisting() {
        when(repo.existsById(5L)).thenReturn(false);
        // Test+Assert
        assertThatThrownBy(() -> this.service.delete(5L))
          .isInstanceOf(NotFoundException.class);
        verify(repo).existsById(5L);
        verifyNoMoreInteractions(repo);
    }

}
