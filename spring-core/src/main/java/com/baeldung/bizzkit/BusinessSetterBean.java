package com.baeldung.bizzkit;

import org.springframework.beans.factory.annotation.Autowired;

public class BusinessSetterBean {

    private long id;

    private String name;

    private BusinessType type;

    @Autowired
    public void setType(BusinessType type) {
        this.type = type;
    }

}