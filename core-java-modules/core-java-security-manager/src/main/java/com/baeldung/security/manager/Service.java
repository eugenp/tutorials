package com.baeldung.security.manager;

public class Service {

    public static final String OPERATION = "my-operation";

    public void operation() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new CustomPermission(OPERATION));
        }
        System.out.println("Operation is executed");
    }

    public static void main(String[] args) {
        new Service().operation();
    }
}
