package com.baeldung.thymeleaf.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.baeldung.thymeleaf.config.WebApp;
import com.baeldung.thymeleaf.config.WebMVCConfig;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebApp.class, WebMVCConfig.class })
public class HomeControllerIntegrationTest {
    private static final String CTX_OBJECT_MSG = "Server Time Using the #ctx Object Is: ";
    private static final String IF_CONDITIONAL_MSG = "Server Time Using #th:if Conditional Is: ";
    private static final String UNLESS_CONDITIONAL_MSG = "Server Time Using #th:unless Conditional Is: ";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .build();
    }

    @Test
    public void whenVariableIsDefined_thenCtxObjectContainsVariable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/variable-defined"))
            .andExpect(status().isOk())
            .andExpect(view().name("checkVariableIsDefined.html"))
            .andExpect(content().string(containsString(CTX_OBJECT_MSG)));
    }

    @Test
    public void whenVariableNotDefined_thenCtxObjectDoesNotContainVariable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/variable-not-defined"))
            .andExpect(status().isOk())
            .andExpect(view().name("checkVariableIsDefined.html"))
            .andExpect(content().string(not(containsString(CTX_OBJECT_MSG))));
    }

    @Test
    public void whenVariableIsDefined_thenIfConditionalIsTrue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/variable-defined"))
            .andExpect(status().isOk())
            .andExpect(view().name("checkVariableIsDefined.html"))
            .andExpect(content().string(containsString(IF_CONDITIONAL_MSG)));
    }

    @Test
    public void whenVariableIsNotDefined_thenIfConditionalIsFalse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/variable-not-defined"))
            .andExpect(status().isOk())
            .andExpect(view().name("checkVariableIsDefined.html"))
            .andExpect(content().string(not(containsString(IF_CONDITIONAL_MSG))));
    }

    @Test
    public void whenVariableIsDefinedAndNotTrue_thenIfConditionalIsFalse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/variable-defined"))
            .andExpect(status().isOk())
            .andExpect(view().name("checkVariableIsDefined.html"))
            .andExpect(content().string(not(containsString("Evaluating \"false\""))))
            .andExpect(content().string(not(containsString("Evaluating \"no\""))))
            .andExpect(content().string(not(containsString("Evaluating \"off\""))))
            .andExpect(content().string(not(containsString("Evaluating 0"))));
    }

    @Test
    public void whenVariableIsDefined_thenUnlessConditionalIsTrue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/variable-defined"))
            .andExpect(status().isOk())
            .andExpect(view().name("checkVariableIsDefined.html"))
            .andExpect(content().string(containsString(IF_CONDITIONAL_MSG)));
    }

    @Test
    public void whenVariableIsNotDefined_thenUnlessConditionalIsFalse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/variable-not-defined"))
            .andExpect(status().isOk())
            .andExpect(view().name("checkVariableIsDefined.html"))
            .andExpect(content().string(not(containsString(UNLESS_CONDITIONAL_MSG))));
    }
}