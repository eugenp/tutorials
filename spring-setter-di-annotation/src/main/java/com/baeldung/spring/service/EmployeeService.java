package com.baeldung.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.spring.dao.IEmployeeDAO;

@Service
public class EmployeeService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	
	private IEmployeeDAO employeeDAO;
	
	@Autowired
	public void setEmployeeDAO(IEmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}
	
	public void printEmployeeMessage() {
		logger.debug(employeeDAO.getMessage());
	}
	
}
