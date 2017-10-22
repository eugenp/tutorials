package com.baeldung.bizzkit;

public class BusinessXMLApplication {
    
    private long id;

    private String name;

    private BusinessService service;
    
    public BusinessXMLApplication(BusinessService service) {
        this.service = service;
    }

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

    public void setService(BusinessService service) {
        this.service = service;
    }

    public boolean processBusinessInfo(long id, String name) {
        return this.service.showBusiness(id, name);
    }
    
}
