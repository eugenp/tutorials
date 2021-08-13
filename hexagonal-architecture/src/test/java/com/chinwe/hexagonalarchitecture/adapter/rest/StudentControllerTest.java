package com.chinwe.hexagonalarchitecture.adapter.rest;

import com.chinwe.hexagonalarchitecture.domain.model.Student;
import com.chinwe.hexagonalarchitecture.domain.responsestatus.Status;
import com.chinwe.hexagonalarchitecture.port.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private StudentService studentService;


    @Test
    void givenStudents_whenGetStudents_thenReturnJsonArray() throws Exception {
        Student studentOne = new Student("Tony", 1L, 100L);
        Student  studentTwo = new Student("Chinwe", 2L, 200L);
        Student  studentThree = new Student("Frank", 3L, 300L);
        Status statusOne =new Status(200,"Student added successfully");
        Status statusTwo =new Status(200,"Student was removed successfully");
        List<Student> students = Arrays.asList(studentOne, studentTwo, studentThree);
        given(studentService.getStudents()).willReturn(students);
        mvc.perform(get("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].studentName", is(studentOne.getStudentName())))
                .andExpect(jsonPath("$[1].studentName", is(studentTwo.getStudentName())));
        verify(studentService, VerificationModeFactory.times(1)).getStudents();
        reset(studentService);
    }

    @Test
    void givenStudent_whenGetStudentById() throws Exception {

        Student studentOne = new Student("Tony", 1L, 100L);

        given(studentService.getStudentById(Mockito.anyLong())).willReturn(studentOne);

        mvc.perform(get("/api/v1/student/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.studentName", is(studentOne.getStudentName())));

        verify(studentService, VerificationModeFactory.times(1)).getStudentById(Mockito.anyLong());
        reset(studentService);

    }

    @Test
    void whenPostStudent_thenCreateStudent() throws Exception {
        Status statusOne =new Status(200,"Student added successfully");
        given(studentService.addStudent(Mockito.any())).willReturn(statusOne);
        mvc.perform(post("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(statusOne))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode", is(statusOne.getStatusCode())));
        verify(studentService, VerificationModeFactory.times(1)).addStudent(Mockito.any());
        reset(studentService);
    }

    @Test
    void whenDeleteStudent_thenRemoveValidStudent() throws Exception {
        Status statusOne =new Status(200,"Student added successfully");
        given(studentService.removeStudent(Mockito.anyLong())).willReturn(statusOne);
        mvc.perform(delete("/api/v1/student/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(statusOne.getStatusCode())));
        verify(studentService, VerificationModeFactory.times(1)).removeStudent(Mockito.anyLong());
        reset(studentService);
    }
}