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

import com.baeldung.jacksonlazyfields.dao.DepartmentRepository;
import com.baeldung.jacksonlazyfields.model.Course;
import com.baeldung.jacksonlazyfields.model.Department;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class DepartmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void whenPostDepartment_thenDepartmentIsCreatedAndReturned() throws Exception {
        Department department = new Department();
        department.setName("Physics");
        String json = objectMapper.writeValueAsString(department);

        ResultActions result = mockMvc.perform(post("/departments").contentType(MediaType.APPLICATION_JSON)
            .content(json));

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("Physics"));
    }

    @Test
    void whenGetDepartment_thenReturnDepartment() throws Exception {
        Department department = new Department();
        department.setName("Math");
        department = departmentRepository.save(department);

        mockMvc.perform(get("/departments/" + department.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(department.getId()))
            .andExpect(jsonPath("$.name").value("Math"));
    }

    @Test
    void whenPostCourseToDepartment_thenReturnDepartmentDto() throws Exception {
        Department department = new Department();
        department.setName("Chemistry");
        department = departmentRepository.save(department);

        Course course = new Course();
        course.setName("Organic Chemistry");
        String json = objectMapper.writeValueAsString(course);

        mockMvc.perform(post("/departments/" + department.getId() + "/course").contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(department.getId()))
            .andExpect(jsonPath("$.name").value("Chemistry"))
            .andExpect(jsonPath("$.courseNames[0]").value("Organic Chemistry"));
    }
}
