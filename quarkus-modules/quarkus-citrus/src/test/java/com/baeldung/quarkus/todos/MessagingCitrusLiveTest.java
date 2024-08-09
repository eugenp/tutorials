package com.baeldung.quarkus.todos;

import static org.citrusframework.actions.ReceiveMessageAction.Builder.receive;
import static org.citrusframework.dsl.MessageSupport.MessageBodySupport.fromBody;
import static org.citrusframework.http.actions.HttpActionBuilder.http;
import static org.citrusframework.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

import jakarta.ws.rs.core.MediaType;

import org.citrusframework.GherkinTestActionRunner;
import org.citrusframework.annotations.CitrusConfiguration;
import org.citrusframework.annotations.CitrusEndpoint;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.kafka.endpoint.KafkaEndpoint;
import org.citrusframework.message.MessageType;
import org.citrusframework.quarkus.CitrusSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.baeldung.quarkus.todos.config.BoundaryCitrusConfig;
import com.baeldung.quarkus.todos.config.EmbeddedKafkaCitrusConfig;
import com.baeldung.quarkus.todos.config.KafkaCitrusConfig;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@CitrusSupport
@CitrusConfiguration(classes = { BoundaryCitrusConfig.class, KafkaCitrusConfig.class, EmbeddedKafkaCitrusConfig.class })
class MessagingCitrusLiveTest {

    @CitrusEndpoint(name = KafkaCitrusConfig.TODOS_EVENTS_TOPIC)
    KafkaEndpoint todosEvents;
    @CitrusEndpoint(name = BoundaryCitrusConfig.API_CLIENT)
    HttpClient apiClient;

    @CitrusResource
    GherkinTestActionRunner t;

    @Test
    void whenCreateItem_thenProduceMessageToKafka() {
        t.when(http().client(apiClient)
            .send()
            .post("/api/v1/todos")
            .message()
            .contentType(MediaType.APPLICATION_JSON)
            .body("{\"title\": \"test\"}"));
        t.then(http().client(apiClient)
            .receive()
            .response(HttpStatus.CREATED)
            .extract(fromBody().expression("$.id", "todoId")));
        t.and(receive().endpoint(todosEvents)
            .message()
            .type(MessageType.JSON)
            .validate(jsonPath().expression("$.title", "test")
                .expression("$.id", "${todoId}")));
    }
}
