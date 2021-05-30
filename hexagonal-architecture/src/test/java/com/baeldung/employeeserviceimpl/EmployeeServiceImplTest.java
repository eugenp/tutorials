package com.baeldung.employeeserviceimpl;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.assertj.core.api.Assertions;
import com.baeldung.domain.Employee;
import com.baeldung.domain.serviceimpl.EmployeeServiceImpl;
import com.baeldung.outboundports.EmployeeRepository;

public class EmployeeServiceImplTest {

	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;

	private Employee employee;

	@Mock
	EmployeeRepository employeeRepository;

	@BeforeEach
	public void setUp() {

		employeeServiceImpl = new EmployeeServiceImpl();
		employee = new Employee();
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testCreateEmployeeInfo() {
		employee.setDesignation("Developer");
		employee.setEmpId(1);
		employee.setEmpName("Mr. Davis Tom");

		Mockito.doNothing().when(employeeRepository).createEmployeeInfo(any());
		employeeServiceImpl.createEmployeeInfo(employee);

		Mockito.verify(employeeRepository, Mockito.times(1)).createEmployeeInfo(employee);

	}

	@Test
	public void testGetEmployeeInfo() {
		employee.setDesignation("Developer");
		employee.setEmpId(1);
		employee.setEmpName("Mr. Davis Tom");

		Mockito.when(employeeRepository.getEmployeeInfo(1)).thenReturn(employee);

		Employee emp = employeeServiceImpl.getEmployeeInfo(1);

		Assertions.assertThat(emp.getEmpName()).isEqualTo("Mr. Davis Tom");
		Assertions.assertThat(emp.getDesignation()).isEqualTo("Developer");

	}
}
