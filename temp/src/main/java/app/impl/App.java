package app.impl;

import app.ApiInterface;
import model.Employee;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.EmployeeService;

@SpringBootApplication
public class App implements ApiInterface {
    private EmployeeService service;

    public App(EmployeeService service) {
        this.service = service;
    } // TODO implement HTTP endpoint

    public Employee get(String id) {
        return service.search(id);
    }
}

