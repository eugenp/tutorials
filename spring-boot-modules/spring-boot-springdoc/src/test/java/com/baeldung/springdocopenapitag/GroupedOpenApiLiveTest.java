package com.baeldung.springdocopenapitag;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import io.swagger.v3.oas.models.tags.Tag;

@SpringBootTest
@AutoConfigureMockMvc
class GroupedOpenApiLiveTest {

    public

    @TestConfiguration
    static class TestGroupConfig {

        @Bean
        public GroupedOpenApi testGroupedApi() {
            return GroupedOpenApi.builder()
                .group("test-group")
                .pathsToMatch("/test-group/**")
                .addOpenApiCustomizer(openApi -> openApi.tags(Arrays.asList(new Tag().name("GroupFirst"), new Tag().name("GroupSecond"))))
                .build();
        }

        @Bean
        public GroupedOpenApi testGroupedApi2() {
            return GroupedOpenApi.builder()
                .group("test-group-2")
                .pathsToMatch("/test-group/**")
                .addOpenApiCustomizer(openApi -> openApi.tags(Arrays.asList(new Tag().name("GroupFirst2"), new Tag().name("GroupSecond2"))))
                .build();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGroupedApiOrder() throws Exception {
        mockMvc.perform(get("/v3/api-docs/test-group"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tags[0].name").value("GroupFirst"))
            .andExpect(jsonPath("$.tags[1].name").value("GroupSecond"));

        mockMvc.perform(get("/v3/api-docs/test-group-2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tags[0].name").value("GroupFirst2"))
            .andExpect(jsonPath("$.tags[1].name").value("GroupSecond2"));
    }
}
