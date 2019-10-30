package com.baeldung.traits

import spock.lang.Specification

class TraitsUnitTest extends Specification {

    Employee employee
    Dog dog

    void setup () {
        employee = new Employee()
        dog = new Dog()
    }

    def 'Should return msg string when using Employee.sayHello method provided by UserTrait' () {
        when:
            def msg = employee.sayHello()
        then:
            msg
            msg instanceof String
            assert msg == "Hello!"
    }
    
    def 'Should return displayMsg string when using Employee.showName method' () {
        when:
            def displayMsg = employee.showName()
        then:
            displayMsg
            displayMsg instanceof String
            assert displayMsg == "Hello, Bob!"
    }
    
    def 'Should return greetMsg string when using Employee.greet method' () {
        when:
            def greetMsg = employee.greet()
        then:
            greetMsg
            greetMsg instanceof String
            assert greetMsg == "Hello, from a private method!"
    }
    
    def 'Should return MissingMethodException when using Employee.greetingMessage method' () {
        when:
            def exception
            try {
                employee.greetingMessage()
            }catch(Exception e) {
                exception = e
            }
            
        then:
            exception
            exception instanceof groovy.lang.MissingMethodException
            assert exception.message == "No signature of method: com.baeldung.traits.Employee.greetingMessage()"+
            " is applicable for argument types: () values: []"
    }
    
    def 'Should return employee instance when using Employee.whoAmI method' () {
        when:
            def emp = employee.self()
        then:
            emp
            emp instanceof Employee
            assert emp.is(employee)
    }
    
    def 'Should display lastName when using Employee.showLastName method' () {
        when:
            def lastNameMsg = employee.showLastName()
        then:
            lastNameMsg
            lastNameMsg instanceof String
            assert lastNameMsg == "Hello, Marley!"
    }
    
    def 'Should be able to define properties of UserTrait in Employee instance' () {
        when:
            employee = new Employee(email: "a@e.com", address: "baeldung.com")
        then:
            employee
            employee instanceof Employee
            assert employee.email == "a@e.com"
            assert employee.address == "baeldung.com"
    }
    
    def 'Should execute basicAbility method from SpeakingTrait and return msg string' () {
        when:
            def speakMsg = dog.basicAbility()
        then:
            speakMsg
            speakMsg instanceof String
            assert speakMsg == "Speaking!!"
    }
    
    def 'Should verify multiple inheritance with traits and execute overridden traits method' () {
        when:
            def walkSpeakMsg = dog.speakAndWalk()
            println walkSpeakMsg
        then:
            walkSpeakMsg
            walkSpeakMsg instanceof String
            assert walkSpeakMsg == "Walk and speak!!"
    }
    
    def 'Should implement AnimalTrait at runtime and access basicBehavior method' () {
        when:
            def dogInstance = new Dog() as AnimalTrait
            def basicBehaviorMsg = dogInstance.basicBehavior()
        then:
            basicBehaviorMsg
            basicBehaviorMsg instanceof String
            assert basicBehaviorMsg == "Animalistic!!"
    }
}