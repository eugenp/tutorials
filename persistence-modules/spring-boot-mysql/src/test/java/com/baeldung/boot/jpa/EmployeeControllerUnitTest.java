package com.baeldung.boot.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerUnitTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployeeId_whenGetEmployeeCalled_ThenReturnEmployee() throws Exception {
        Employee employeeExpected = new Employee();
        employeeExpected.setEmpName("Test Emp");
        employeeExpected.setEmpDoj(LocalDate.now());
        employeeExpected.setJobTitle("Manager");

        Mockito.when(employeeRepository.findById(1234)).thenReturn(Optional.of(employeeExpected));

        MvcResult result = mockMvc.perform(get("/employee/1234"))
            .andExpect(status().isOk()).andReturn();

        Employee employee = objectMapper.readValue(result.getResponse().getContentAsString(), Employee.class);
        assertEquals(employeeExpected.getEmpName(), employee.getEmpName());
        assertEquals(employeeExpected.getJobTitle(), employee.getJobTitle());
        assertEquals(employeeExpected.getEmpDoj(), employee.getEmpDoj());
    }

    @Test
    public void givenEmployee_whenCreateEmployeeCalled_ThenReturnEmployee() throws Exception {
        Employee employeeExpected = new Employee();
        employeeExpected.setEmpName("Test Emp");
        employeeExpected.setEmpDoj(LocalDate.now());
        employeeExpected.setJobTitle("Manager");

        Mockito.when(employeeRepository.save(employeeExpected)).thenReturn(employeeExpected);

        MvcResult result = mockMvc.perform(post("/employee")
            .content(objectMapper.writeValueAsString(employeeExpected))
            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        Employee employee = objectMapper.readValue(result.getResponse().getContentAsString(), Employee.class);
        assertEquals(employeeExpected.getEmpName(), employee.getEmpName());
        assertEquals(employeeExpected.getJobTitle(), employee.getJobTitle());
        assertEquals(employeeExpected.getEmpDoj(), employee.getEmpDoj());
    }
}
