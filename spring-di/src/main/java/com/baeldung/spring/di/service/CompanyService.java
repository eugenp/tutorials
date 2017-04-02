package com.baeldung.spring.di.service;

import com.baeldung.spring.di.dao.EmployeeDao;

import lombok.Data;

@Data
public class CompanyService {
    private EmployeeDao dao;
    
    CompanyService(EmployeeDao dao){
        this.dao = dao;
    }

}
