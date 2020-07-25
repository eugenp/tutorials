package com.baeldung.modules.main;

import com.baeldung.modules.hello.HelloInterface;
import com.baeldung.modules.hello.HelloModules;
import java.util.ServiceLoader;

public class MainApp {
    public static void main(String[] args) {
        HelloModules.doSomething();
        
        Iterable<HelloInterface> services = ServiceLoader.load(HelloInterface.class);
        HelloInterface service = services.iterator().next();
        service.sayHello();
    }
}
