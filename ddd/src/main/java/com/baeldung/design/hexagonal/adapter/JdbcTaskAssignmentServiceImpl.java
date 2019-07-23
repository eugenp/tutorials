package com.baeldung.design.hexagonal.adapter;

import com.baeldung.design.hexagonal.model.Employee;
import com.baeldung.design.hexagonal.model.Task;
import com.baeldung.design.hexagonal.port.TaskAssignmentService;

public class JdbcTaskAssignmentServiceImpl implements TaskAssignmentService {

	@Override
	public void assignTask(Task empTask, Employee assignee) {
		//JDBC details to successfully persist the assignment
	}

}
