package com.baeldung.spring.dao.concrete;

import com.baeldung.spring.dao.IEmployeeDAO;

public class EmployeeDAO implements IEmployeeDAO {

	@Override
	public String getMessage() {
		return "This is employee message";
	}

}
