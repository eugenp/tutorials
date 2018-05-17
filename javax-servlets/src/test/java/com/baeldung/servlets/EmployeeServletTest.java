package com.baeldung.servlets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.baeldung.model.Employee;
import com.google.gson.Gson;


@RunWith(MockitoJUnitRunner.class)
public class EmployeeServletTest {
	
	@Mock
	HttpServletRequest httpServletRequest;
	
	@Mock
	HttpServletResponse httpServletResponse;
	
	Employee employee;
	
	@Test
    public void whenPostRequestToEmployeeServlet_thenCorrect() throws Exception {

		//Given
		int id = 1;
        String name = "Karan Khanna";
        String department = "IT";
        Double salary = 5000.0;
        employee = new Employee(id, name, department, salary);
        
        //when
		when(httpServletRequest.getParameter("id")).thenReturn(String.valueOf(id));
        when(httpServletRequest.getParameter("name")).thenReturn(name);
        when(httpServletRequest.getParameter("department")).thenReturn(department);
        when(httpServletRequest.getParameter("salary")).thenReturn(String.valueOf(salary));
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(httpServletResponse.getWriter()).thenReturn(pw);

        EmployeeServlet employeeServlet = new EmployeeServlet();
        employeeServlet.doPost(httpServletRequest, httpServletResponse);
        
        String employeeJsonString = sw.getBuffer().toString().trim();
        Employee fetchedEmployee = new Gson().fromJson(employeeJsonString, Employee.class);
        assertEquals(fetchedEmployee, employee);
    }

}
