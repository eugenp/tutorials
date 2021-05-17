package com.baeldung.hexagonal.studentapp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc
public class StudentControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void addStudent() throws Exception {
        //GIVEN
        String student = "{\"id\": 1, \"name\":\"ankit\",\"group\":\"group1\"}";

        //WHEN-THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/students")
                .content(student)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void getStudents() throws Exception {

        //GIVEN
        List<Student> students = Arrays.asList(new Student(1L, "ankit", "group1"), new Student(2L, "amit", "group2"));
        Mockito.when(studentService.getStudents()).thenReturn(students);

        String expectedResult = "[{\"id\":1, \"name\":\"ankit\",\"group\":\"group1\"}" +
                ",{\"id\":2, \"name\":\"amit\",\"group\":\"group2\"}]";

        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/students")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //THEN
        Assert.assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
        JSONAssert.assertEquals(mvcResult.getResponse().getContentAsString(), expectedResult, false);
    }
}
