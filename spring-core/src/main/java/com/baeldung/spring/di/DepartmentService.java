package com.baeldung.spring.di;

import org.springframework.stereotype.Component;

@Component
public class DepartmentService {
    public String getDepartment() {
        return "Management";
    }
}
