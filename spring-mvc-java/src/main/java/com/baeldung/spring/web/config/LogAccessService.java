package com.baeldung.spring.web.config;

import com.baeldung.spring.web.services.FileLogService;
import com.baeldung.spring.web.services.LogService;
import com.baeldung.spring.web.services.SocketLogService;

public class LogAccessService {

    private String service;
    private LogService serviceImpl;

    public void setService(String service) {
        this.service = service;
    }

    public String logAccess(String logPoint, int id) {
        if (service.compareTo("socket") == 0) {
            serviceImpl = new SocketLogService();
        }
        if (service.compareTo("file") == 0) {
            serviceImpl = new FileLogService();
        }
        return serviceImpl.logAccess("\"Employee " + id + " entered " + logPoint + "\"");
    }
}
