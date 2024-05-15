package com.baeldung.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.baeldung.schema.Employee;
import com.baeldung.schema.EmployeeKey;

@Service
public class AvroProducer {

    @Autowired
    private Processor processor;

    public void produceEmployeeDetails(int empId, String firstName, String lastName) {

        // creating employee details
        Employee employee = new Employee();
        employee.setId(empId);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDepartment("IT");
        employee.setDesignation("Engineer");

        // creating partition key for kafka topic
        EmployeeKey employeeKey = new EmployeeKey();
        employeeKey.setId(empId);
        employeeKey.setDepartmentName("IT");

        Message<Employee> message = MessageBuilder.withPayload(employee)
            .setHeader(KafkaHeaders.MESSAGE_KEY, employeeKey)
            .build();

        processor.output()
            .send(message);
    }

}
