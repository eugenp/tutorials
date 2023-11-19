package com.baeldung.traits

trait UserTrait implements Human {

    String email
    String address

    abstract String name()

    String sayHello() {
        return "Hello!"
    }

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
}
