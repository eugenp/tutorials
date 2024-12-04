package com.splunk.log4j.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.splunk.log4j.dto.Student;
import com.splunk.log4j.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenAddStudentCalled_thenReturnSuccessAndAddedStudent() throws Exception {
        Student student = new Student();
        student.setName("Ram");
        student.setRollNumber(3);

        when(studentService.addStudent(student)).thenReturn(student);

        mockMvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(student)))
          .andExpect(status().isOk())
          .andExpect(content().json(objectMapper.writeValueAsString(student)))
          .andReturn();
    }

    @Test
    void whenGetStudentCalled_thenReturnStudentByIndex() throws Exception {
        Student student = new Student();
        student.setName("Ram");
        student.setRollNumber(4);

        when(studentService.getStudent(0)).thenReturn(student);

        mockMvc.perform(get("/students/0"))
          .andExpect(status().isOk())
          .andExpect(content().json(objectMapper.writeValueAsString(student)));
    }

    @Test
    void whenGetStudentsCalled_thenReturnListOfStudent() throws Exception {
        Student student = new Student();
        student.setName("Ram");
        student.setRollNumber(4);

        Student student2 = new Student();
        student.setName("Sham");
        student.setRollNumber(3);

        when(studentService.getStudents()).thenReturn(List.of(student, student2));

        mockMvc.perform(get("/students"))
          .andExpect(status().isOk())
          .andExpect(content().json(objectMapper.writeValueAsString(List.of(student, student2))));
    }
}
