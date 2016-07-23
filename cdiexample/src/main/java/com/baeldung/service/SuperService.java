package com.baeldung.service;

import com.baeldung.interceptor.Audited;

public class SuperService {
    @Audited
    public String deliverService(String uid) {
        System.out.println("Service delivered for uid:" + uid);
        return uid;
    }
}
