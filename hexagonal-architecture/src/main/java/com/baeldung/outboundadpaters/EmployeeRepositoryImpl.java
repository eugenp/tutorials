package com.baeldung.outboundadpaters;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.domain.Employee;
import com.baeldung.outboundports.EmployeeRepository;

@Repository 
public class EmployeeRepositoryImpl implements EmployeeRepository { 
	
	private HashMap<Integer, Employee> empDB = new HashMap<Integer, Employee>(); 
	
	@Override 
	public void createEmployeeInfo(Employee emp) { 
		empDB.put(emp.getEmpId(), emp); 
	} 
	
	@Override 
	public Employee getEmployeeInfo(Integer empId) { 
		return empDB.get(empId); 
	} 
	
	@Override 
	public List<Employee> getAllEmployeesInfo() { 
		return empDB.values().stream().collect(Collectors.toList()); 
	} 
}
