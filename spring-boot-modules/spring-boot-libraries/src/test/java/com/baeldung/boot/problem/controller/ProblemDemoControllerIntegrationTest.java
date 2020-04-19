package com.baeldung.boot.problem.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.boot.problem.SpringProblemApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SpringProblemApplication.class)
@AutoConfigureMockMvc
public class ProblemDemoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenRequestingAllTasks_thenReturnSuccessfulResponseWithArrayWithTwoTasks() throws Exception {
        mockMvc.perform(get("/tasks").contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andExpect(jsonPath("$.length()", equalTo(2)))
            .andExpect(status().isOk());
    }

    @Test
    public void whenRequestingExistingTask_thenReturnSuccessfulResponse() throws Exception {
        mockMvc.perform(get("/tasks/1").contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andExpect(jsonPath("$.id", equalTo(1)))
            .andExpect(status().isOk());
    }

    @Test
    public void whenRequestingMissingTask_thenReturnNotFoundProblemResponse() throws Exception {
        mockMvc.perform(get("/tasks/5").contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
            .andDo(print())
            .andExpect(jsonPath("$.title", equalTo("Not found")))
            .andExpect(jsonPath("$.status", equalTo(404)))
            .andExpect(jsonPath("$.detail", equalTo("Task '5' not found")))
            .andExpect(status().isNotFound());
    }

    @Test
    public void whenMakePutCall_thenReturnNotImplementedProblemResponse() throws Exception {
        mockMvc.perform(put("/tasks/1").contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
            .andDo(print())
            .andExpect(jsonPath("$.title", equalTo("Not Implemented")))
            .andExpect(jsonPath("$.status", equalTo(501)))
            .andExpect(status().isNotImplemented());
    }

    @Test
    public void whenMakeDeleteCall_thenReturnForbiddenProblemResponse() throws Exception {
        mockMvc.perform(delete("/tasks/2").contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
            .andDo(print())
            .andExpect(jsonPath("$.title", equalTo("Forbidden")))
            .andExpect(jsonPath("$.status", equalTo(403)))
            .andExpect(jsonPath("$.detail", equalTo("You can't delete this task")))
            .andExpect(status().isForbidden());
    }
    
    @Test
    public void whenMakeGetCallWithInvalidIdFormat_thenReturnBadRequestResponseWithStackTrace() throws Exception {
        mockMvc.perform(get("/tasks/invalid-id").contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
            .andDo(print())
            .andExpect(jsonPath("$.title", equalTo("Bad Request")))
            .andExpect(jsonPath("$.status", equalTo(400)))
            .andExpect(jsonPath("$.stacktrace", notNullValue()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void whenMakeGetCallWithInvalidIdFormat_thenReturnBadRequestResponseWithCause() throws Exception {
        mockMvc.perform(get("/tasks/invalid-id").contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
            .andDo(print())
            .andExpect(jsonPath("$.title", equalTo("Bad Request")))
            .andExpect(jsonPath("$.status", equalTo(400)))
            .andExpect(jsonPath("$.cause", notNullValue()))
            .andExpect(jsonPath("$.cause.title", equalTo("Internal Server Error")))
            .andExpect(jsonPath("$.cause.status", equalTo(500)))
            .andExpect(jsonPath("$.cause.detail", containsString("For input string:")))
            .andExpect(jsonPath("$.cause.stacktrace", notNullValue()))
            .andExpect(status().isBadRequest());
    }

}
