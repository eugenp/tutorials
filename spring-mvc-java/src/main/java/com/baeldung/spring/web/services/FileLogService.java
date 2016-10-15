package com.baeldung.spring.web.services;

public class FileLogService implements LogService {
    public String logAccess(String logInfo) {
        String ret = "Logging access on FILE: " + logInfo;
        System.out.println(ret);
        return ret;
    }
}
