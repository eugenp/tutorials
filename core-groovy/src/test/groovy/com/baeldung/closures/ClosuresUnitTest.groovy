package com.baeldung.closures

import spock.lang.Specification

class ClosuresUnitTest extends GroovyTestCase {

    Closures closures = new Closures()
    
    void testClosures() {
        
        closures.print("Hello! Closure")
        closures.formatToLowerCaseClosure("Hello! Closure")
        
        closures.print.call("Hello! Closure")
        closures.formatToLowerCaseClosure.call("Hello! Closure")
        
        assert closures.stringUpperCase("Hello! Closure") == "HELLO! CLOSURE"

        assert closures.multiply(2, 4) == 8

        assert closures.calculate(12, 4, "ADD") == 16
        assert closures.calculate(12, 4, "SUB") == 8
        assert closures.calculate(43, 8, "DIV") == 5.375
        
        assert closures.exchange(40, "RON") == 40*4.6
        assert closures.exchange(100, "USD") == 110.0
                
        //closures vs methods
        
        assert closures.formatToLowerCase("TONY STARK") == closures.formatToLowerCaseClosure("Tony STark")
        
        //parameters
        assert closures.greet("Alex") == "Hello! Alex"
        
        assert closures.addAll(12, 10, 14) == 36
        
        assert closures.volume({ l, b -> return l*b }, 12, 6, 10) == 720
        
        assert closures.volume({ radius -> return Math.PI*radius*radius/3 }, 5, 10) == Math.PI * 250/3
        
        //closure in GStrings
        
        def name = "Samwell"
        def welcomeMsg = "Welcome! $name"
        
        assert welcomeMsg == "Welcome! Samwell"
        
        name = "Tarly"
        
        assert welcomeMsg != "Welcome! Tarly"
        
        def fullName = "Tarly Samson"
        def greetStr = "Hello! ${-> fullName}"
        
        assert greetStr == "Hello! Tarly Samson"

        fullName = "Jon Smith"
        assert greetStr == "Hello! Jon Smith"
        
        //closure in Lists
        def list = [10, 11, 12, 13, 14, true, false, "BUNTHER"]
        list.each {
            println it
        }
        
        assert [13, 14] == list.findAll{ it instanceof Integer && it >= 13}
       
        //closure in Maps
        def map = [1:10, 2:30, 4:5]
        
        assert [10, 60, 20] == map.collect{it.key * it.value}
        
        /*Delegation Strategy*/
        
        //this
        assert closures.self() instanceof Closures
        assert closures.nestedSelf() instanceof Closures
        
        //owner
        assert closures.self() == closures.classOwner()
        assert closures.classOwner() != closures.objectOwner() //classOwner returns class instance and objectOwner returns object instance
        
        //delegate
        assert closures.self() == closures.ownerDelegate()
        assert closures.enclosedDelegate() == closures.enclosedDelegate
        
        //Set object to delegate
        def employee = new Employee(fullName: "Wall E")
        closures.upperCaseFullName.delegate = employee
        assert closures.upperCaseFullName() == "WALL E"
        
        def delegationMap = [fullName: "Smith, Jon"]
        
        closures.upperCaseFullName.delegate = delegationMap
        assert closures.upperCaseFullName() == "SMITH, JON"
        
        //closure vs lambda
        
        def players = ["A", "B", "C", "D", "E"]
        
        players.each {
            println it
        }
        
        //List<String> playerStream = players.stream().collect(Collectors.toList());
        //playerStream.forEach(System.out::println);
        
    }
    
}