package service.impl;

import service.GreetingPort;

public class GreetingPortHelloWorldAdapter implements GreetingPort {
    @Override
    public void greet() {
        System.out.println("Hello World");
    }
}
