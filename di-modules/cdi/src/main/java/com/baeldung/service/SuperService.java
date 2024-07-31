package com.baeldung.service;

import com.baeldung.interceptor.Audited;

public class SuperService {
    @Audited
    public String deliverService(String uid) {
        return uid;
    }
}
