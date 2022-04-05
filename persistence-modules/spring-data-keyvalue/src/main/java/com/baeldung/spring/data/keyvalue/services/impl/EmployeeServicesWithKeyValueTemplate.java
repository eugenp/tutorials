package com.baeldung.spring.data.keyvalue.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Sort;
import org.springframework.data.keyvalue.core.KeyValueTemplate;
import org.springframework.data.keyvalue.core.query.KeyValueQuery;
import org.springframework.stereotype.Service;

import com.baeldung.spring.data.keyvalue.services.EmployeeService;
import com.baeldung.spring.data.keyvalue.vo.Employee;

@Service("employeeServicesWithKeyValueTemplate")
@DependsOn("keyValueTemplate")
public class EmployeeServicesWithKeyValueTemplate implements EmployeeService {
	
	@Autowired
	@Qualifier("keyValueTemplate")
	KeyValueTemplate keyValueTemplate;

	@Override
	public void save(Employee employee) {
		keyValueTemplate.insert(employee);
	}

	@Override
	public Optional<Employee> get(Integer id) {
		return keyValueTemplate.findById(id, Employee.class);
	}

	@Override
	public Iterable<Employee> fetchAll() {
		return keyValueTemplate.findAll(Employee.class);
	}

	@Override
	public void update(Employee employee) {
		keyValueTemplate.update(employee);
	}

	@Override
	public void delete(Integer id) {
		keyValueTemplate.delete(id, Employee.class);
	}

	@Override
	public Iterable<Employee> getSortedListOfEmployeesBySalary() {
		KeyValueQuery query = new KeyValueQuery();
		query.setSort(Sort.by(Sort.Direction.DESC, "salary"));
		return keyValueTemplate.find(query, Employee.class);
	}

}
