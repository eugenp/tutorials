package com.baeldung.spring.data.keyvalue.services.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.data.keyvalue.SpringDataKeyValueApplication;
import com.baeldung.spring.data.keyvalue.repositories.EmployeeRepository;
import com.baeldung.spring.data.keyvalue.services.EmployeeService;
import com.baeldung.spring.data.keyvalue.vo.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringDataKeyValueApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeServicesWithRepositoryIntegrationTest {

	@Autowired
	@Qualifier("employeeServicesWithRepository")
    EmployeeService employeeService;

	@Autowired
    EmployeeRepository employeeRepository;

	static Employee employee1;

	@BeforeClass
	public static void setUp() {
		employee1 = new Employee(1, "Karan", "IT", "5000");
	}

	@Test
	public void test1_whenEmployeeSaved_thenEmployeeIsAddedToMap() {
		employeeService.save(employee1);
		assertEquals(employeeRepository.findById(1).get(), employee1);
	}

	@Test
	public void test2_whenEmployeeGet_thenEmployeeIsReturnedFromMap() {
		Employee employeeFetched = employeeService.get(1).get();
		assertEquals(employeeFetched, employee1);
	}

	@Test
	public void test3_whenEmployeesFetched_thenEmployeesAreReturnedFromMap() {
		List<Employee> employees = (List<Employee>)employeeService.fetchAll();
		assertEquals(employees.size(), 1);
		assertEquals(employees.get(0), employee1);
	}

	@Test
	public void test4_whenEmployeeUpdated_thenEmployeeIsUpdatedToMap() {
		employee1.setName("Pawan");
		employeeService.update(employee1);
		assertEquals(employeeRepository.findById(1).get().getName(),"Pawan");
	}

	@Test
	public void test5_whenEmployeeDeleted_thenEmployeeIsRemovedMap() {
		employeeService.delete(1);
		assertEquals(employeeRepository.findById(1).isPresent(), false);
	}

}
