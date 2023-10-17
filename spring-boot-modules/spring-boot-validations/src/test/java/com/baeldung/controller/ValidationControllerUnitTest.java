package com.baeldung.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.service.ValidationService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ValidationController.class)
class ValidationControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public ValidationService validationService() {
            return new ValidationService() {
            };
        }
    }

    @Autowired
    ValidationService service;

    @Test
    void whenNullInputForBooleanField_thenHttpBadRequestAsHttpResponse() throws Exception {
        String postBody = "{\"boolField\":null,\"trueField\":true,\"falseField\":false,\"boolStringVar\":\"+\"}";

        mockMvc.perform(post("/validateBoolean").contentType("application/json")
            .content(postBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidInputForTrueBooleanField_thenErrorResponse() throws Exception {
        String postBody = "{\"boolField\":true,\"trueField\":false,\"falseField\":false,\"boolStringVar\":\"+\"}";

        String output = mockMvc.perform(post("/validateBoolean").contentType("application/json")
            .content(postBody))
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("trueField must have true value", output);
    }

    @Test
    void whenInvalidInputForFalseBooleanField_thenErrorResponse() throws Exception {
        String postBody = "{\"boolField\":true,\"trueField\":true,\"falseField\":true,\"boolStringVar\":\"+\"}";

        String output = mockMvc.perform(post("/validateBoolean").contentType("application/json")
            .content(postBody))
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("falseField must have false value", output);
    }

    @Test
    void whenInvalidBooleanFromJson_thenErrorResponse() throws Exception {
        String postBody = "{\"boolField\":true,\"trueField\":true,\"falseField\":false,\"boolStringVar\":\"plus\"}";

        String output = mockMvc.perform(post("/validateBoolean").contentType("application/json")
            .content(postBody))
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("Only values accepted as Boolean are + and -", output);
    }

    @Test
    void whenAllBooleanFieldsValid_thenCorrectResponse() throws Exception {
        String postBody = "{\"boolField\":true,\"trueField\":true,\"falseField\":false,\"boolStringVar\":\"+\"}";

        String output = mockMvc.perform(post("/validateBoolean").contentType("application/json")
            .content(postBody))
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("BooleanObject is valid", output);
    }

    @Test
    void givenAllBooleanFieldsValid_whenServiceValidationFails_thenErrorResponse() throws Exception {
        mockMvc.perform(post("/validateBooleanAtService").contentType("application/json"))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void whenNullInputForTrueBooleanField_thenCorrectResponse() throws Exception {
        String postBody = "{\"boolField\":true,\"trueField\":null,\"falseField\":false,\"boolStringVar\":\"+\"}";

        mockMvc.perform(post("/validateBoolean").contentType("application/json")
            .content(postBody))
            .andExpect(status().isOk());
    }

    @Test
    void whenNullInputForFalseBooleanField_thenHttpBadRequestAsHttpResponse() throws Exception {
        String postBody = "{\"boolField\":true,\"trueField\":true,\"falseField\":null,\"boolStringVar\":\"+\"}";

        String output = mockMvc.perform(post("/validateBoolean").contentType("application/json")
            .content(postBody))
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("falseField cannot be null", output);
    }
}
