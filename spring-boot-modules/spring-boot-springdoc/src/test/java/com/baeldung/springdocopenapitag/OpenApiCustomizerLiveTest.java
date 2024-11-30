package com.baeldung.springdocopenapitag;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import io.swagger.v3.oas.models.tags.Tag;

@SpringBootTest
@AutoConfigureMockMvc
public class OpenApiCustomizerLiveTest {

    @TestConfiguration
    static class TestCustomiserConfig {

        @Bean
        public OpenApiCustomizer testTagOrderCustomiser() {
            return openApi -> {
                List<Tag> tags = Arrays.asList(new Tag().name("CustomFirst"), new Tag().name("CustomSecond"));
                openApi.setTags(tags);
            };
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCustomiserOrder() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tags[0].name").value("CustomFirst"))
            .andExpect(jsonPath("$.tags[1].name").value("CustomSecond"));
    }
}
