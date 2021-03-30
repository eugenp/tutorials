package com.baeldung.pattern.hexagonal.controller;

import com.baeldung.pattern.hexagonal.HexArchApplicationDemo;
import com.baeldung.pattern.hexagonal.domain.model.Employee;
import com.baeldung.pattern.hexagonal.domain.services.EmployeeService;
import com.baeldung.pattern.hexagonal.persistence.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HexArchApplicationDemo.class)
@WebAppConfiguration
class EmployeeControllerTest {

    @MockBean
    EmployeeRepository repository;

    private MockMvc mockMvc;

    private Employee testModel;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

    }

    @Test
    public void testGetEmployee() throws Exception {

        testModel = new Employee();
        testModel.setEmpId("2000");
        testModel.setEmpName("Test user 1");
        testModel.setEmpJobTitle("Software engineer");

        when(repository.findById(anyString())).thenReturn(Optional.of(testModel));

        final MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/employees/2000"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.empId").value("2000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.empName").value("Test user 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.empJobTitle").value("Software engineer"))
                .andReturn();
    }


}