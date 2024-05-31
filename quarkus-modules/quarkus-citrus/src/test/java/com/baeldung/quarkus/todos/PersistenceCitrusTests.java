package com.baeldung.quarkus.todos;

import com.baeldung.quarkus.todos.config.BoundaryCitrusConfig;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.citrusframework.GherkinTestActionRunner;
import org.citrusframework.annotations.CitrusConfiguration;
import org.citrusframework.annotations.CitrusEndpoint;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.quarkus.CitrusSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import javax.sql.DataSource;

import static org.citrusframework.actions.ExecuteSQLAction.Builder.sql;
import static org.citrusframework.dsl.MessageSupport.MessageBodySupport.fromBody;
import static org.citrusframework.http.actions.HttpActionBuilder.http;

@QuarkusTest
@CitrusSupport
@CitrusConfiguration(classes = {
        BoundaryCitrusConfig.class
})
class PersistenceCitrusTests {

    @CitrusEndpoint(name = BoundaryCitrusConfig.API_CLIENT)
    HttpClient apiClient;
    @CitrusResource
    GherkinTestActionRunner t;
    @Inject
    DataSource dataSource;

    @Test
    void shouldReturn201OnCreateItem() {
        t.when(
                http()
                        .client(apiClient)
                        .send()
                        .post("/api/v1/todos")
                        .message()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"title\": \"test\"}")
        );
        t.then(
                http()
                        .client(apiClient)
                        .receive()
                        .response(HttpStatus.CREATED)
                        // save new id to test context variable "todoId"
                        .extract(fromBody().expression("$.id", "todoId"))
        );
        t.then(
                sql()
                        .dataSource(dataSource)
                        .query()
                        .statement("select title from todos where id=${todoId}")
                        .validate("title", "test")
        );
    }

}
