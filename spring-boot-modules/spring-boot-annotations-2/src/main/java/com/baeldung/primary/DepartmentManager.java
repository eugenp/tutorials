package com.baeldung.primary;

import org.springframework.stereotype.Component;

@Component
public class DepartmentManager implements Manager {
    @Override
    public String getManagerName() {
        return "Department manager";
    }
}
