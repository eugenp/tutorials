package com.baeldung.springdocopenapitag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CustomOpenAPIConfigLiveTest {

    @Autowired
    private OpenAPI openAPI;

    @Configuration
    static class TestOpenAPIConfig {

        @Bean
        public OpenAPI testOpenAPI() {
            return new OpenAPI().tags(Arrays.asList(new Tag().name("TestFirst")
                .description("First"), new Tag().name("TestSecond")
                .description("Second")));
        }
    }

    @Test
    public void whenOpenApiInitialized_thenTagsAreInDefinedOrder() {
        List<Tag> tags = openAPI.getTags();
        assertNotNull(tags);
        assertEquals(2, tags.size());
        assertEquals("TestFirst", tags.get(0)
            .getName());
        assertEquals("TestSecond", tags.get(1)
            .getName());
    }

}
