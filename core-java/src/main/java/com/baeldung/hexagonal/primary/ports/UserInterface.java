package com.baeldung.hexagonal.primary.ports;

public interface UserInterface {

    public String loginUser(String uid, String pwd);

    public String registerUser(String uid, String pwd);
}
