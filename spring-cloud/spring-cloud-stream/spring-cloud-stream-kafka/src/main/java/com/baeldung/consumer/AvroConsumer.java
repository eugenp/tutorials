package com.baeldung.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.stereotype.Service;

import com.baeldung.schema.Employee;

@Service
public class AvroConsumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @StreamListener(Processor.INPUT)
    public void consumeEmployeeDetails(Employee employeeDetails) {
        logger.info("Let's process employee details: {}", employeeDetails);
    }

}
