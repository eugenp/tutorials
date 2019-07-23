package com.baeldung.design.hexagonal.adapter;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.design.hexagonal.infra.KafkaTopic;
import com.baeldung.design.hexagonal.model.Employee;
import com.baeldung.design.hexagonal.model.Task;
import com.baeldung.design.hexagonal.port.TaskAssignmentService;

public class KafkaTaskAssignmentServiceImpl implements TaskAssignmentService	{
	
	@Autowired
	private KafkaTopic assignmentTopic;
	
	@Override
	public void assignTask(Task empTask, Employee assignee) {
		//Assignment of task via Kafka topic goes here
	}

}
