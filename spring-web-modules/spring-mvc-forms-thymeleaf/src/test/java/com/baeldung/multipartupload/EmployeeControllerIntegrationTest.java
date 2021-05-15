package com.baeldung.multipartupload;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.apache.http.entity.ContentType.DEFAULT_BINARY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@EnableWebMvc
public class EmployeeControllerIntegrationTest {

    private static final MockMultipartFile A_FILE = new MockMultipartFile("document", null, DEFAULT_BINARY.toString(), "Employee Record".getBytes());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void givenFormData_whenPost_thenReturns200OK() throws Exception {

        mockMvc.perform(multipart("/employee")
          .file(A_FILE)
          .param("name", "testname"))
          .andExpect(status().isOk());
    }

    @Test
    public void givenEmployeeJsonAndMultipartFile_whenPostWithRequestPart_thenReturnsOK() throws Exception {
        MockMultipartFile employeeJson = new MockMultipartFile("employee", null,
          "application/json", "{\"name\": \"Emp Name\"}".getBytes());

        mockMvc.perform(multipart("/requestpart/employee")
          .file(A_FILE)
          .file(employeeJson))
          .andExpect(status().isOk());
    }

    @Test
    public void givenRequestPartAndRequestParam_whenPost_thenReturns200OK() throws Exception {
        mockMvc.perform(multipart("/requestparam/employee")
          .file(A_FILE)
          .param("name", "testname"))
          .andExpect(status().isOk());
    }
}