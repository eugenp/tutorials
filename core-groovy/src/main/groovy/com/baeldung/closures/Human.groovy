package com.baeldung.closures

abstract class Human {
    
    abstract String getName()
    
    def greet() {
        return "Hello, $name"
    }
    
}