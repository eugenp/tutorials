package com.baeldung.cdi.service;

import com.baeldung.cdi.interceptor.Audited;

public class SuperService {
    @Audited
    public String deliverService(String uid) {
        System.out.println("Service delivered for uid:" + uid);
        return uid;
    }
}
