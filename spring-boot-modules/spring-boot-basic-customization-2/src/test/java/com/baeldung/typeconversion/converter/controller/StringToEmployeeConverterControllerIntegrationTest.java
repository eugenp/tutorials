package com.baeldung.typeconversion.converter.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.typeconversion.Application;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
public class StringToEmployeeConverterControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getStringToEmployeeTest() throws Exception {
        mockMvc.perform(get("/string-to-employee?employee=1,2000")).andDo(print()).andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.salary", is(2000.0))).andExpect(status().isOk());
    }
}
