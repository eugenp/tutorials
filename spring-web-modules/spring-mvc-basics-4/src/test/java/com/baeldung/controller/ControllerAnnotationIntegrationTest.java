package com.baeldung.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.baeldung.controller.student.Student;
import com.baeldung.validation.listvalidation.SpringListValidationApplication;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringListValidationApplication.class)
public class ControllerAnnotationIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    private Student selectedStudent;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
            .build();
        selectedStudent = new Student();
        selectedStudent.setId(1);
        selectedStudent.setName("Peter");
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testTestController() throws Exception {
        ModelAndView mv = mockMvc.perform(MockMvcRequestBuilders.get("/test"))
            .andReturn()
            .getModelAndView();

        // validate modal data
        assertSame(mv.getModelMap()
            .get("data")
            .toString(), "Welcome home man");
        // validate view name
        assertSame(mv.getViewName(), "welcome");
    }

    @Test
    public void testRestController() throws Exception {
        String responseBody = mockMvc.perform(MockMvcRequestBuilders.get("/student/{studentId}", 1))
            .andReturn()
            .getResponse()
            .getContentAsString();
        Student studentDetails = objectMapper.readValue(responseBody, Student.class);

        assertEquals(selectedStudent, studentDetails);

    }

    @Test
    public void testRestAnnotatedController() throws Exception {
        String responseBody = mockMvc.perform(MockMvcRequestBuilders.get("/annotated/student/{studentId}", 1))
            .andReturn()
            .getResponse()
            .getContentAsString();
        Student studentDetails = objectMapper.readValue(responseBody, Student.class);

        assertEquals(selectedStudent, studentDetails);
    }

}
