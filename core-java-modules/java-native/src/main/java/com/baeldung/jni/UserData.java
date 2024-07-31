package com.baeldung.jni;

public class UserData {
    
    public String name;
    public double balance;
    
    public String getUserInfo() {
        return "[name]=" + name + ", [balance]=" + balance;
    }
}
