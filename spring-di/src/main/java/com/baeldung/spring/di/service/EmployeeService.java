package com.baeldung.spring.di.service;

import com.baeldung.spring.di.dao.EmployeeDao;

import lombok.Data;

@Data
public class EmployeeService {
    private EmployeeDao dao;
    
    EmployeeService(EmployeeDao dao){
        this.dao = dao;
    }

}
