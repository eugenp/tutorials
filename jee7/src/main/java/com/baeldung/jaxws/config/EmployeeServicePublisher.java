package com.baeldung.jaxws.config;

import com.baeldung.jaxws.EmployeeServiceImpl;

import javax.xml.ws.Endpoint;

public class EmployeeServicePublisher {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/employeeservice", new EmployeeServiceImpl());
    }
}
