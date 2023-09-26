package com.baeldung.system;

public class PropertiesExample {
    public void getUserName() {
        String username = System.getProperty("user.name");
        System.out.println("User: " + username);
    }

    public void getCustomProp() {
        String customProperty = System.getProperty("custom.prop");
        System.out.println("Custom property: " + customProperty);
    }

    public void getCustomPropWithFallback() {
        String customProperty = System.getProperty("non-existent-property", "default value");
        System.out.println("Custom property: " + customProperty);
    }

}
