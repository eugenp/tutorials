package com.baeldung.bizzkit;

import org.springframework.beans.factory.annotation.Autowired;

public class BusinessConstructorBean {
    
    private long id;
    
    private String name;
    
    private BusinessType type;

    @Autowired
    public BusinessConstructorBean(BusinessType type) {
        this.type = type;
    }
    
}
