package com.baeldung.sample.boundary;

import com.baeldung.sample.control.NotFoundException;
import com.baeldung.sample.control.Todo;
import com.baeldung.sample.control.TodosService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integrationstests der Todos REST API auf HTTP Layer. Die Anwendung wird als Black Box behandelt.
 * Somit müssen alle Tests, die einen existierenden Datensatz benötigen, diesen im Test anlegen.
 * Spring Boot startet den sog. "ApplicationContext" automatisch und bietet Möglichkeiten, die Anwendungsteile vom Test aus aufzurufen.
 */
@WebMvcTest
@ContextConfiguration(classes = TodosBoundaryLayer.class)
class TodosControllerApiIntegrationTest {

    private static final String BASEURL = "/api/v1/todos"; // URL to Resource
    private static final String DEFAULT_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;

    @MockBean
    TodosService service;
    @Autowired
    MockMvc mvc; // testing by sending HTTP requests and verifying HTTP responses
    @Autowired
    ObjectMapper mapper; // used to render or parse JSON

    /*
     * Testfall:
     *  - GET auf alle Todos -> 200 OK mit JSON
     */
    @DisplayName("GET auf alle Daten (200 OK)")
    @Test
    void testFindAllTodos() throws Exception {
        when(service.findAll()).thenReturn(List.of());
        mvc
          .perform(get(BASEURL).accept(DEFAULT_MEDIA_TYPE))
          .andExpect(status().isOk())
          .andExpect(content().contentType(DEFAULT_MEDIA_TYPE));
    }

    /*
     * Testfall:
     *  - einzelnes Todos auslesen -> kein Fehler
     */
    @Test
    void testFindById() throws Exception {
        when(service.findById(1L))
          .thenReturn(Optional.of(new Todo(1L, "test")));
        mvc
          .perform(get(BASEURL + "/1").accept(DEFAULT_MEDIA_TYPE))
          .andExpect(status().isOk())
          .andExpect(content().contentType(DEFAULT_MEDIA_TYPE))
          .andExpect(jsonPath("$.title").value("test"));
    }

    /*
     * Testfall:
     *  - einzelnes Todos auslesen -> 404
     */
    @Test
    void testFindByIdNotExisting() throws Exception {
        when(service.findById(1L))
          .thenReturn(Optional.empty());
        mvc
          .perform(get(BASEURL + "/1").accept(DEFAULT_MEDIA_TYPE))
          .andExpect(status().isNotFound());
    }

    /*
     * Testfall:
     *  - Anlegen eines Todos per POST -> 201 mit Location Header
     */
    @DisplayName("POST liefert 201 mit Location-Header")
    @Test
    void testCreateTodo() throws Exception {
        // etwas umständlich, die ID zu besetzen, wenn auf dem Service create() aufgerufen wird
        when(service.create(any())).thenReturn(new Todo(5L, "test-todo"));
        final var json = "{\"title\":\"test-todo\"}";
        mvc
          .perform(post(BASEURL).contentType(DEFAULT_MEDIA_TYPE).content(json))
          .andExpect(status().isCreated())
          .andExpect(header().exists(HttpHeaders.LOCATION))
          .andExpect(jsonPath("$.id").value(5L));
    }

    /*
     * Testfall:
     *  - Anlegen eines Todos per POST ohne Titel -> 422
     */
    @DisplayName("POST erzeugt kein Todo, wenn kein Titel angegeben ist")
    @Test
    void testCreateTodoWithoutTitle() throws Exception {
        final var json = "{}";
        mvc
          .perform(post(BASEURL).contentType(DEFAULT_MEDIA_TYPE).content(json))
          .andExpect(status().isUnprocessableEntity());
        verifyNoInteractions(service);
    }

    /*
     * Testfall:
     *  - Anlegen eines Todos per POST mit ID -> 400
     */
    @DisplayName("POST erzeugt kein Todo, wenn die ID mitgegeben wird (undefinierte Property)")
    @Test
    void testCreateTodoWithID() throws Exception {
        final var json = "{\"id\":1, \"title\":\"test\"}";
        mvc
          .perform(post(BASEURL).contentType(DEFAULT_MEDIA_TYPE).content(json))
          .andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }

    /*
     * Testfall:
     *  - Anlegen eines Todos per POST mit leerem Titel -> 400
     */
    @DisplayName("POST erzeugt kein Todo, wenn Titel weniger als 1 Zeichen hat")
    @Test
    void testCreateTodoWithEmptyTitle() throws Exception {
        final var newTodo = new TodoRequestDto();
        newTodo.setTitle("");
        final String json = mapper.writeValueAsString(newTodo);
        this.mvc //
          .perform(post(BASEURL).contentType(DEFAULT_MEDIA_TYPE).content(json)) //
          .andExpect(status().isUnprocessableEntity());
    }

    /*
     * Testfall:
     *  - Ändern -> 204
     */
    @Test
    void testUpdateTodo() throws Exception {
        final var json = "{\"title\":\"test-todo\"}";
        mvc
          .perform(put(BASEURL + "/5").contentType(DEFAULT_MEDIA_TYPE).content(json))
          .andExpect(status().isNoContent());
    }

    /*
     * Testfall:
     *  - Ändern -> 404
     */
    @Test
    void testUpdateTodoNotExisting() throws Exception {
        doThrow(NotFoundException.class).when(service).update(any());
        final var json = "{\"title\":\"test-todo\"}";
        mvc
          .perform(put(BASEURL + "/5").contentType(DEFAULT_MEDIA_TYPE).content(json))
          .andExpect(status().isNotFound());
    }

    /*
     * Testfall:
     *  - Ändern des Todos mit leerem Titel
     */
    @Test
    @DisplayName("PUT mit leerem Titel")
    void testUpdateWithEmptyTitle() throws Exception {
        // Act
        final var json = "{}";
        mvc
          .perform(put(BASEURL + "/5").contentType(DEFAULT_MEDIA_TYPE).content(json))
          .andExpect(status().isUnprocessableEntity());
        verifyNoInteractions(service);
    }

    /*
     * Testfall:
     *  - Löschen -> 204
     */
    @Test
    void testDeleteTodo() throws Exception {
        mvc
          .perform(delete(BASEURL + "/5").contentType(DEFAULT_MEDIA_TYPE))
          .andExpect(status().isNoContent());
    }

    /*
     * Testfall:
     *  - Löschen -> 404
     */
    @Test
    void testDeleteTodoNotExisting() throws Exception {
        doThrow(NotFoundException.class).when(service).delete(anyLong());
        mvc
          .perform(delete(BASEURL + "/5").contentType(DEFAULT_MEDIA_TYPE))
          .andExpect(status().isNotFound());
    }

}
