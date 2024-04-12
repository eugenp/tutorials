package com.baeldung.mongoauth.service;

public interface SecurityService {
    boolean login(String username, String password);
}
