package com.baeldung.spring.data.gemfire.function;

import org.springframework.data.gemfire.function.annotation.GemfireFunction;
import org.springframework.stereotype.Component;

@Component
public class FunctionImpl {

    @GemfireFunction
    public void greeting(String message){
        System.out.println("Message "+message);
    }

    @GemfireFunction
    public String sayHello(String message){
        return "Hello "+message;
    }
}
