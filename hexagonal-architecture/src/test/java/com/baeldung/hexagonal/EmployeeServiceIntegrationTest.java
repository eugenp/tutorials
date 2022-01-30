package com.baeldung.hexagonal;

import com.baeldung.hexagonal.controller.EmployeeController;
import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeServiceIntegrationTest {

    @Autowired
    MockMvc mock;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void givenEmployee_whenFindEmployeeById_thenReturnValidEmployee() throws Exception {
        Employee emp = new Employee(Long.valueOf(1), "Sarah", "Jones");
        given(employeeService.findEmployeeById(Mockito.anyLong())).willReturn(emp);

        mock.perform(get("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(emp.getId().intValue())));

        verify(employeeService, VerificationModeFactory.times(1)).findEmployeeById(Mockito.anyLong());
        reset(employeeService);
    }
}
