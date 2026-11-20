package com.baeldung.solon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Connection;
import java.sql.Statement;
import java.time.Duration;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.noear.snack4.ONode;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Inject;
import org.noear.solon.test.SolonTest;
import org.noear.solon.test.annotation.Rollback;

import com.baeldung.solon.model.Task;
import com.baeldung.solon.service.TaskService;

@SolonTest(value = App.class, env = "test", enableHttp = true, delay = 0, debug = false)
public class TaskApiIntegrationTest {

    private static HttpClient client;

    @Inject("${server.port}")
    private int port;

    @Inject("tasks")
    private DataSource dataSource;

    @Inject
    private TaskService taskService;

    @BeforeAll
    static void createClient() {
        client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(5))
            .build();
    }

    @BeforeEach
    void clearTasks() throws Exception {
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM tasks");
        }
    }

    @AfterEach
    void verifyRollbackAndCleanUp(TestInfo testInfo) throws Exception {
        try {
            if (testInfo.getTestMethod().orElseThrow().isAnnotationPresent(Rollback.class)) {
                assertTrue(taskService.findAll().isEmpty(), "The annotated test must roll back its insert");
            }
        } finally {
            clearTasks();
        }
    }

    @AfterAll
    static void stopApplication() {
        client.close();
        Solon.stopBlock(false, 0);
    }

    @Test
    void givenName_whenGreeting_thenReturnPlainText() throws Exception {
        HttpResponse<String> response = request("GET", "/hello?name=Baeldung", null);

        assertEquals(200, response.statusCode());
        assertEquals("Hello, Baeldung!", response.body());
        assertEquals("Hello, World!", request("GET", "/hello", null).body());
    }

    @Test
    void whenCreatingUpdatingAndDeletingTask_thenPersistChangesAcrossRequests() throws Exception {
        HttpResponse<String> created = request("POST", "/tasks", """
            {"title":"  Learn Solon  "}
            """);

        assertEquals(201, created.statusCode());
        ONode task = ONode.ofJson(created.body());
        long id = task.get("id").getLong();
        String path = "/tasks/" + id;
        assertEquals(path, created.headers().firstValue("Location").orElseThrow());
        assertEquals("Learn Solon", task.get("title").getString());
        assertFalse(task.get("completed").getBoolean());

        HttpResponse<String> fetched = request("GET", path, null);
        assertEquals(200, fetched.statusCode());
        assertEquals("Learn Solon", ONode.ofJson(fetched.body()).get("title").getString());
        assertEquals(1, ONode.ofJson(request("GET", "/tasks", null).body()).size());

        HttpResponse<String> updated = request("PUT", path, """
            {"title":"Learn Solon REST APIs","completed":true}
            """);
        assertEquals(200, updated.statusCode());
        ONode saved = ONode.ofJson(request("GET", path, null).body());
        assertEquals("Learn Solon REST APIs", saved.get("title").getString());
        assertTrue(saved.get("completed").getBoolean());

        HttpResponse<String> deleted = request("DELETE", path, null);
        assertEquals(204, deleted.statusCode());
        assertEquals("", deleted.body());
        assertEquals(404, request("GET", path, null).statusCode());
        assertEquals(0, ONode.ofJson(request("GET", "/tasks", null).body()).size());
    }

    @Test
    void givenBlankTitle_whenCreatingTask_thenReturnBadRequestWithoutPersisting() throws Exception {
        HttpResponse<String> response = request("POST", "/tasks", "{\"title\":\" \"}");

        assertEquals(400, response.statusCode());
        assertEquals("Title must not be blank", ONode.ofJson(response.body()).get("message").getString());
        assertTrue(taskService.findAll().isEmpty());
    }

    @Test
    void givenMissingTask_whenUpdatingOrDeleting_thenReturnNotFound() throws Exception {
        assertEquals(404, request("PUT", "/tasks/99999", "{\"title\":\"Missing\",\"completed\":true}").statusCode());
        assertEquals(404, request("DELETE", "/tasks/99999", null).statusCode());
    }

    @Test
    @Rollback
    public void whenCreatingTaskWithinTransaction_thenReadUncommittedTask() {
        Task task = taskService.create("Temporary task");

        assertEquals("Temporary task", taskService.findById(task.getId()).getTitle());
    }

    private HttpResponse<String> request(String method, String path, String body) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + path))
            .timeout(Duration.ofSeconds(5));
        HttpRequest.BodyPublisher publisher = body == null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(body);
        if (body != null) {
            builder.header("Content-Type", "application/json");
        }
        return client.send(builder.method(method, publisher).build(), BodyHandlers.ofString());
    }
}
