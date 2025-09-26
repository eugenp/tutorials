package com.baeldung.jacksonlazyfields.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.baeldung.jacksonlazyfields.dao.CourseRepository;
import com.baeldung.jacksonlazyfields.model.Course;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CourseRepository courseRepository;

    @Test
    void whenPostCourse_thenCourseIsCreatedAndReturned() throws Exception {
        Course course = new Course();
        course.setName("Algebra");
        String json = objectMapper.writeValueAsString(course);

        ResultActions result = mockMvc.perform(post("/courses").contentType(MediaType.APPLICATION_JSON)
            .content(json));

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("Algebra"));
    }

    @Test
    void whenGetCourse_thenReturnCourse() throws Exception {
        Course course = new Course();
        course.setName("Geometry");
        course = courseRepository.save(course);

        mockMvc.perform(get("/courses/" + course.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(course.getId()))
            .andExpect(jsonPath("$.name").value("Geometry"));
    }
}
