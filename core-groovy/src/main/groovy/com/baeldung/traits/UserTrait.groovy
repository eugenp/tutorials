package com.baeldung.traits

trait UserTrait implements Human {
    
    String sayHello() {
        return "Hello!"
    }
    
    abstract String name()
    
    String showName() {
       return "Hello, ${name()}!"
    }

    private String greetingMessage() {
        return 'Hello, from a private method!'
    }
    
    String greet() {
        def msg = greetingMessage()
        println msg
        msg
    }
    
    def self() {
        return this 
    }
    
    String showLastName() {
        return "Hello, ${lastName()}!"
    }
    
    String email
    String address
}
    