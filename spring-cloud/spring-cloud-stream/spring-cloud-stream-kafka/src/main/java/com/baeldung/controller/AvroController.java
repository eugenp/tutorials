package com.baeldung.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.producer.AvroProducer;

@RestController
public class AvroController {

    @Autowired
    private AvroProducer avroProducer;

    @GetMapping("/employees/{id}/{firstName}/{lastName}")
    public String producerAvroMessageV1(@PathVariable int id, @PathVariable String firstName, @PathVariable String lastName) {
        avroProducer.produceEmployeeDetails(id, firstName, lastName);
        return "Sent employee details to consumer";
    }

}
