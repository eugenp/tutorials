package com.baeldung.architecture.hexagonal.employee.domain.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.baeldung.architecture.hexagonal.employee.domain.dto.Employee;
import com.baeldung.architecture.hexagonal.employee.domain.repository.EmployeeRepository;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {
	@Mock
	private EmployeeRepository employeeRepository;
	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Test
	public void addEmployee_givenEmployeeRepository_thenSaveIt() {
		//Given
		Employee employeeToRepository = new Employee("Vikash", "Agrawal", 1);
		when(employeeRepository.addEmployee(any(Employee.class)))
		  .thenReturn(employeeToRepository);
		
		//When
		Employee employeeFromDomain = employeeService.addEmployee("Vikash", "Agrawal");
		
		//Then
		assertEquals(1, employeeFromDomain.getEmployeeId());
		assertEquals("Vikash", employeeFromDomain.getFirstName());
		assertEquals("Agrawal", employeeFromDomain.getLastName());
	}
	
	@Test
	public void findById_givenEmployeeRepository_thenRetrieveIt() throws Exception {
		//Given
		Employee employeeToRepository = new Employee("Vikash", "Agrawal", 1);
		when(employeeRepository.findById(1))
		  .thenReturn(employeeToRepository);
		
		//When
		Employee employeeFromDomain = employeeService.findById(1);
		
		//Then
		assertEquals(1, employeeFromDomain.getEmployeeId());
		assertEquals("Vikash", employeeFromDomain.getFirstName());
		assertEquals("Agrawal", employeeFromDomain.getLastName());
	}
}
