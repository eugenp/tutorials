package com.baeldung.modules.hello;

public class HelloModules implements HelloInterface {
    public static void doSomething() {
        System.out.println("Hello, Modules!");
    }

    public void sayHello() {
        System.out.println("Hello!");
    }
}
