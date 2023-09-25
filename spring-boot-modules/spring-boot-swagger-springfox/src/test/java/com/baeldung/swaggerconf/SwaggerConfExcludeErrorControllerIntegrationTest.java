package com.baeldung.swaggerconf;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SwaggerConfExcludeErrorControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenCallingSwaggerJSON_stringObjectDoesNotContainAnyErrorControllers() throws Exception {
        ResultActions resultActions = mvc.perform(get("/v2/api-docs")).andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String content = result.getResponse().getContentAsString();
        Assert.assertNotNull(content);
        Assert.assertFalse(content.contains("error-controller"));
    }
}