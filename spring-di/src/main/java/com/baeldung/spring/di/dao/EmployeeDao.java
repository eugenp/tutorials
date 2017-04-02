package com.baeldung.spring.di.dao;

import lombok.Data;

@Data
public class EmployeeDao {
    private String datasource;
    
    public EmployeeDao(String datasource){
        this.datasource = datasource;
    }

}
