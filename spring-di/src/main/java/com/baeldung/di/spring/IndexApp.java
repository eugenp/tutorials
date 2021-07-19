package com.baeldung.di.spring;

public class IndexApp {

    private IService service;

    public String getServiceValue() {
        return service.serve();
    }

    public IService getService() {
        return service;
    }

    public void setService(IService service) {
        this.service = service;
    }

}
