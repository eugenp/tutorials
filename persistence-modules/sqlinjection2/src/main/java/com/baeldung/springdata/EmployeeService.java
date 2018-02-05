package com.baeldung.springdata;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.model.Employee;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeRepository empRepository;

    public List<Employee> searchEmployee(String name) {
        List<Employee> list = new ArrayList<>();
        empRepository.findByName(name)
            .forEach(e -> list.add(e));
        return list;
    }
}
