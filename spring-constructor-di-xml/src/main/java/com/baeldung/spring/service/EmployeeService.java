package com.baeldung.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.spring.dao.IEmployeeDAO;

public class EmployeeService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	
	private IEmployeeDAO employeeDAO;
	
	public EmployeeService(IEmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}
	
	public void printEmployeeMessage() {
		logger.debug(employeeDAO.getMessage());
	}
	
}
