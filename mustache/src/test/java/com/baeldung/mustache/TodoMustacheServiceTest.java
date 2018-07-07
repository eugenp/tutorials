package com.baeldung.mustache;

import com.baeldung.mustache.model.Todo;
import com.github.mustachejava.Mustache;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoMustacheServiceTest {

    private String executeTemplate(Mustache m, Map<String, Object> context) throws IOException {
        StringWriter writer = new StringWriter();
        m.execute(writer, context).flush();
        return writer.toString();
    }

    private String executeTemplate(Mustache m, Todo todo) throws IOException {
        StringWriter writer = new StringWriter();
        m.execute(writer, todo).flush();
        return writer.toString();
    }

    @Test
    public void givenTodoObject_whenGetHtml_thenSuccess() throws IOException {
        Todo todo = new Todo("Todo 1", "Todo description");
        Mustache m = MustacheUtil.getMustacheFactory().compile("todo.mustache");
        Map<String, Object> context = new HashMap<>();
        context.put("todo", todo);

        String expected = "<h2>Todo 1</h2>";
        assertThat(executeTemplate(m, todo)).contains(expected);
    }

    @Test
    public void givenNullTodoObject_whenGetHtml_thenEmptyHtml() throws IOException {
        Mustache m = MustacheUtil.getMustacheFactory().compile("todo-section.mustache");
        Map<String, Object> context = new HashMap<>();
        assertThat(executeTemplate(m, context)).isEmpty();
    }

    @Test
    public void givenTodoList_whenGetHtml_thenSuccess() throws IOException {
        Mustache m = MustacheUtil.getMustacheFactory().compile("todos.mustache");

        List<Todo> todos = Arrays.asList(
          new Todo("Todo 1", "Todo description"),
          new Todo("Todo 2", "Todo description another"),
          new Todo("Todo 3", "Todo description another")
        );
        Map<String, Object> context = new HashMap<>();
        context.put("todos", todos);

        assertThat(executeTemplate(m, context))
          .contains("<h2>Todo 1</h2>")
          .contains("<h2>Todo 2</h2>")
          .contains("<h2>Todo 3</h2>");
    }

    @Test
    public void givenEmptyList_whenGetHtml_thenEmptyHtml() throws IOException {
        Mustache m = MustacheUtil.getMustacheFactory().compile("todos.mustache");

        Map<String, Object> context = new HashMap<>();
        assertThat(executeTemplate(m, context)).isEmpty();
        ;
    }

    @Test
    public void givenEmptyList_whenGetHtmlUsingInvertedSection_thenHtml() throws IOException {
        Mustache m = MustacheUtil.getMustacheFactory().compile("todos-inverted-section.mustache");

        Map<String, Object> context = new HashMap<>();
        assertThat(executeTemplate(m, context).trim()).isEqualTo("<p>No todos!</p>");
    }

    @Test
    public void givenTodoList_whenGetHtmlUsingLamdba_thenHtml() throws IOException {
        Mustache m = MustacheUtil.getMustacheFactory().compile("todos-lambda.mustache");
        List<Todo> todos = Arrays.asList(
          new Todo("Todo 1", "Todo description"),
          new Todo("Todo 2", "Todo description another"),
          new Todo("Todo 3", "Todo description another")
        );
        todos.get(2).setDone(true);
        todos.get(2).setCompletedOn(Date.from(Instant.now().plusSeconds(300)));

        Map<String, Object> context = new HashMap<>();
        context.put("todos", todos);
        assertThat(executeTemplate(m, context).trim()).contains("Done 5 minutes ago");
    }
}
