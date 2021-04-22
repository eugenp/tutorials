package com.baeldung.pattern.adapter.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.baeldung.pattern.domain.Employee;
import com.baeldung.pattern.port.EmployeeService;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void givenEmployeeId_whenGetEmployee_thenSuccessfulResponseReturned() throws Exception {

        Employee employee = anEmployee();
        given(employeeService.getEmployee("emp001")).willReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", "emp001")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.employeeId").value(employee.getEmployeeId()))
            .andExpect(jsonPath("$.department").value(employee.getDepartment()))
            .andExpect(jsonPath("$.bonus").value(employee.getBonus()))
            .andExpect(jsonPath("$.salary").value(employee.getSalary()));
    }

    private static Employee anEmployee() {
        return Employee.builder()
            .employeeId("emp001")
            .department("IT")
            .bonus(new BigDecimal("110"))
            .salary(10000)
            .build();
    }
}
