package com.baeldung.quarkus.todos;

import static org.citrusframework.openapi.actions.OpenApiActionBuilder.openapi;

import org.citrusframework.GherkinTestActionRunner;
import org.citrusframework.annotations.CitrusConfiguration;
import org.citrusframework.annotations.CitrusEndpoint;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.openapi.OpenApiSpecification;
import org.citrusframework.quarkus.CitrusSupport;
import org.citrusframework.spi.Resources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.springframework.http.HttpStatus;

import com.baeldung.quarkus.todos.config.BoundaryOpenApiCitrusConfig;

import io.quarkus.test.junit.QuarkusTest;

@SetSystemProperty(key = "citrus.json.message.validation.strict", value = "false")
@QuarkusTest
@CitrusSupport
@CitrusConfiguration(classes = { BoundaryOpenApiCitrusConfig.class })
class BoundaryOpenApiCitrusLiveTest {

    @CitrusEndpoint(name = BoundaryOpenApiCitrusConfig.API_CLIENT)
    HttpClient apiClient;
    final OpenApiSpecification apiSpecification = OpenApiSpecification.from(Resources.create("classpath:META-INF/resources/openapi.yml"));
    @CitrusResource
    GherkinTestActionRunner t;

    @BeforeEach
    void setup() {
        this.apiSpecification.setGenerateOptionalFields(false);
        this.apiSpecification.setValidateOptionalFields(false);
    }

    @Test
    void whenCreateItem_thenReturn201() {
        t.when(openapi().specification(apiSpecification)
            .client(apiClient)
            .send("createTodo") // operationId
        );
        t.then(openapi().specification(apiSpecification)
            .client(apiClient)
            .receive("createTodo", HttpStatus.CREATED));
    }

}