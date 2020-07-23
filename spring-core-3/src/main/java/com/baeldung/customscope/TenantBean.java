package com.baeldung.customscope;

public class TenantBean {

    private final String name;

    public TenantBean(String name) {
        this.name = name;
    }

    public void sayHello() {
        System.out.println(String.format("Hello from %s of type %s", this.name, this.getClass().getName()));
    }
}
