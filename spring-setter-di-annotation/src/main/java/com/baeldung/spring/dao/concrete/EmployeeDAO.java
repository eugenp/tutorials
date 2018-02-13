package com.baeldung.spring.dao.concrete;

import org.springframework.stereotype.Repository;

import com.baeldung.spring.dao.IEmployeeDAO;

@Repository
public class EmployeeDAO implements IEmployeeDAO {

	@Override
	public String getMessage() {
		return "This is employee message";
	}

}
