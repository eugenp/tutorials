package com.baeldung;

import com.baeldung.service.GreetingPort;
import com.baeldung.service.impl.GreetingPortHelloWorldAdapter;

public class HelloHexagonApplication {
    public static void main(String[] args) {
        GreetingPort greetingPort = new GreetingPortHelloWorldAdapter();
        greetingPort.greet();
    }
}
