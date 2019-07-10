package com.baeldung.traits

class Employee implements UserTrait {
    
    String name() {
        return 'Bob'
    }
    
    String lastName() {
        return "Marley"
    }
}