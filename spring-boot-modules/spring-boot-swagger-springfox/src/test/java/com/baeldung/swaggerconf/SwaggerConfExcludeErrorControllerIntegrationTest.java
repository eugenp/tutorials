package com.baeldung.swaggerconf;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled("springfox-boot-starter is not maintained anymore. Use spring-doc")
class SwaggerConfExcludeErrorControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void whenCallingSwaggerJSON_stringObjectDoesNotContainAnyErrorControllers() throws Exception {
        ResultActions resultActions = mvc.perform(get("/v2/api-docs")).andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertFalse(content.contains("error-controller"));
    }
}