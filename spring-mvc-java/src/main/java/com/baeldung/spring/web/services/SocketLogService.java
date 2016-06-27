package com.baeldung.spring.web.services;

//@Service
public class SocketLogService implements LogService {
    public String logAccess(String logInfo) {
        String ret = "Logging access on SOCKET: " + logInfo;
        System.out.println(ret);
        return ret;
    }
}
