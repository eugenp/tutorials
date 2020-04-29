package com.baeldung.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.baeldung.controller.student.Student;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:test-mvc.xml" })
public class ControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    private Student selectedStudent;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        selectedStudent = new Student();
        selectedStudent.setId(1);
        selectedStudent.setName("Peter");
    }

    @Test
    public void testTestController() throws Exception {

        ModelAndView mv = this.mockMvc.perform(MockMvcRequestBuilders.get("/test/")).andReturn().getModelAndView();

        // validate modal data
        Assert.assertSame(mv.getModelMap().get("data").toString(), "Welcome home man");

        // validate view name
        Assert.assertSame(mv.getViewName(), "welcome");
    }

    @Test
    public void testRestController() throws Exception {

        String responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/student/{studentId}", 1)).andReturn().getResponse().getContentAsString();

        ObjectMapper reader = new ObjectMapper();

        Student studentDetails = reader.readValue(responseBody, Student.class);

        Assert.assertEquals(selectedStudent, studentDetails);

    }

    @Test
    public void testRestAnnotatedController() throws Exception {

        String responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/annotated/student/{studentId}", 1)).andReturn().getResponse().getContentAsString();

        ObjectMapper reader = new ObjectMapper();

        Student studentDetails = reader.readValue(responseBody, Student.class);

        Assert.assertEquals(selectedStudent, studentDetails);
    }
}
