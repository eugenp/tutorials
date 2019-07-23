package com.baeldung.design.hexagonal.port;

import com.baeldung.design.hexagonal.model.Employee;
import com.baeldung.design.hexagonal.model.Task;

public interface TaskAssignmentService {

	public void assignTask(Task empTask, Employee assignee);
}
