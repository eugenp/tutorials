package com.baeldung.filterandinterceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletResponse;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGETRequestWithId_thenResponseStatusISOK() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/details?id=1001"))
            .andReturn();
        assertEquals(result.getResponse()
            .getStatus(), HttpServletResponse.SC_OK);
    }

    @Test
    void whenGETRequestWithoutId_thenResponseStatusISUnAuthorized() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/details"))
            .andReturn();
        assertEquals(result.getResponse()
            .getStatus(), HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void whenGETRequestWithcorrectId_thenResponseIsTrue() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/isEmployee?id=1001"))
            .andReturn();
        assertEquals(result.getResponse()
            .getContentAsString(), "true");
    }

    @Test
    void whenGETRequestWithIncorrectId_thenResponseIsfalse() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/isEmployee?id=1004"))
            .andReturn();
        assertEquals(result.getResponse()
            .getContentAsString(), "false");
    }

}
