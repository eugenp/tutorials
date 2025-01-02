package com.baeldung.springdocopenapitag;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootTest
@AutoConfigureMockMvc
@OpenAPIDefinition(tags = { @Tag(name = "DefinitionFirst"), @Tag(name = "DefinitionSecond") })
public class OpenAPIDefinitionLiveTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testOpenAPIDefinitionOrder() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tags[0].name").value("DefinitionFirst"))
            .andExpect(jsonPath("$.tags[1].name").value("DefinitionSecond"));
    }
}

