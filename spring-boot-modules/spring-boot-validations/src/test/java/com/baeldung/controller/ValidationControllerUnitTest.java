package com.baeldung.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.dto.BooleanObject;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ValidationController.class)
class ValidationControllerUnitTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testNullBoolean() throws Exception {
        BooleanObject boolObj = new BooleanObject();
        boolObj.setBoolField(null);
        String postBody = objectMapper.writeValueAsString(boolObj);

        mockMvc.perform(post("/validateBoolean").contentType("application/json")
            .content(postBody))
            .andExpect(status().isBadRequest());

    }

    @Test
    void testTrueBoolean() throws Exception {
        BooleanObject boolObj = new BooleanObject();
        boolObj.setBoolField(Boolean.TRUE);
        boolObj.setTrueField(Boolean.FALSE);
        String postBody = objectMapper.writeValueAsString(boolObj);

        String output = mockMvc.perform(post("/validateBoolean").contentType("application/json")
            .content(postBody))
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("trueField must have true value", output);
    }

    @Test
    void testFalseBoolean() throws Exception {
        BooleanObject boolObj = new BooleanObject();
        boolObj.setBoolField(Boolean.TRUE);
        boolObj.setTrueField(Boolean.TRUE);
        boolObj.setFalseField(Boolean.TRUE);
        String postBody = objectMapper.writeValueAsString(boolObj);

        String output = mockMvc.perform(post("/validateBoolean").contentType("application/json")
            .content(postBody))
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("falseField must have false value", output);
    }

    @Test
    void testBooleanFromString() throws Exception {
        BooleanObject boolObj = new BooleanObject();
        boolObj.setBoolField(Boolean.TRUE);
        boolObj.setTrueField(Boolean.TRUE);
        boolObj.setFalseField(Boolean.FALSE);
        boolObj.setBoolStringVar(true);
        String postBody = objectMapper.writeValueAsString(boolObj);

        String output = mockMvc.perform(post("/validateBoolean").contentType("application/json")
            .content(postBody))
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("Only values accepted as Boolean are TRUE, FALSE, +, -, 1 , 0", output);
    }

    @Test
    void testInvalidBooleanFromJson() throws Exception {

        String postBody = "{\"boolField\":true,\"trueField\":true,\"falseField\":false,\"boolStringVar\":\"6\"}";

        String output = mockMvc.perform(post("/validateBoolean").contentType("application/json")
            .content(postBody))
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("Only values accepted as Boolean are TRUE, FALSE, +, -, 1 , 0", output);

    }

    @Test
    void testValidBean() throws Exception {

        String postBody = "{\"boolField\":true,\"trueField\":true,\"falseField\":false,\"boolStringVar\":\"+\"}";

        String output = mockMvc.perform(post("/validateBoolean").contentType("application/json")
            .content(postBody))
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("BooleanObject is valid", output);

    }
}
