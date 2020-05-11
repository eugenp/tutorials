package com.baeldung.awsappsync;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AwsAppSyncApplicationTests {

    @Test
    void givenGraphQuery_whenListEvents_thenReturnAllEvents() {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", "query ListEvents {"
                + "  listEvents {"
                + "    items {"
                + "      id"
                + "      name"
                + "      where"
                + "      when"
                + "      description"
                + "    }"
                + "  }"
                + "}");
        requestBody.put("variables", "");
        requestBody.put("operationName", "ListEvents");

        String bodyString = AppSyncClientHelper.getResponseBodySpec(requestBody)
                .bodyToMono(String.class).block();

        assertNotNull(bodyString);
        assertTrue(bodyString.contains("My First Event"));
        assertTrue(bodyString.contains("where"));
        assertTrue(bodyString.contains("when"));
    }

    @Test
    void givenGraphAdd_whenMutation_thenReturnIdNameDesc() {

        String queryString = "mutation add {"
                + "    createEvent("
                + "        name:\"My added GraphQL event\""
                + "        where:\"Day 2\""
                + "        when:\"Saturday night\""
                + "        description:\"Studying GraphQL\""
                + "    ){"
                + "        id"
                + "        name"
                + "        description"
                + "    }"
                + "}";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", queryString);
        requestBody.put("variables", "");
        requestBody.put("operationName", "add");

        WebClient.ResponseSpec response = AppSyncClientHelper.getResponseBodySpec(requestBody);

        String bodyString = response.bodyToMono(String.class).block();

        assertNotNull(bodyString);
        assertTrue(bodyString.contains("My added GraphQL event"));
        assertFalse(bodyString.contains("where"));
        assertFalse(bodyString.contains("when"));
    }
}
