package com.baeldung.toggle;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ToggleApplication.class)
@AutoConfigureMockMvc
public class ToggleIntegrationTest {

    @Autowired
    SalaryService salaryService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac)
            .addFilter(springSecurityFilterChain)
            .build();
    }

    @Test
    public void givenNoAuthentication_whenIncreaseSalary_thenNoIncrease() throws Exception {
        Employee emp = new Employee(1, 2000);
        employeeRepository.save(emp);
        mvc.perform(post("/increaseSalary").param("id", emp.getId() + ""))
            .andExpect(status().is(200));

        emp = employeeRepository.findOne(1L);
        assertEquals("salary incorrect", 2000, emp.getSalary(), 0.5);
    }

    @Test
    public void givenAdminAuthentication_whenIncreaseSalary_thenIncrease() throws Exception {
        Employee emp = new Employee(1, 2000);
        employeeRepository.save(emp);
        mvc.perform(post("/increaseSalary").param("id", emp.getId() + "")
            .with(httpBasic("admin", "pass")))
            .andExpect(status().is(200));

        emp = employeeRepository.findOne(1L);
        assertEquals("salary incorrect", 2200, emp.getSalary(), 0.5);
    }

    @Test
    public void givenUserAuthentication_whenIncreaseSalary_thenNoIncrease() throws Exception {
        Employee emp = new Employee(1, 2000);
        employeeRepository.save(emp);
        mvc.perform(post("/increaseSalary").param("id", emp.getId() + "")
            .with(httpBasic("user", "pass")))
            .andExpect(status().is(200));

        emp = employeeRepository.findOne(1L);
        assertEquals("salary incorrect", 2000, emp.getSalary(), 0.5);
    }

}
