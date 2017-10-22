package com.baeldung.bizzkit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessSetterBean {

    private long id;

    private String name;

    private BusinessService service;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BusinessService getService() {
        return service;
    }

    @Autowired
    public void setType(BusinessService service) {
        this.service = service;
    }

    public boolean processBusinessInfo(long id, String name) {
        return this.service.showBusiness(id, name);
    }

}