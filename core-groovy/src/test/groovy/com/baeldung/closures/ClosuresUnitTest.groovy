package com.baeldung.closures

import spock.lang.Specification

class ClosuresUnitTest extends GroovyTestCase {

    Closures closures = new Closures()
    
    void testClosures() {
        
        closures.print("Hello! Closure")
        closures.formatLowerCaseClosure("Hello! Closure")
        
        closures.print.call("Hello! Closure")
        closures.formatLowerCaseClosure.call("Hello! Closure")
        
        assert closures.stringUpperCase("Hello! Closure") == "HELLO! CLOSURE"

        assert closures.multiply(2, 4) == 8

        assert closures.closureWithTypedParameters("Alex", 2, 4) == "hey Alex the value is 2 and 4"
                
        //closures vs methods
        
        assert closures.formatLowerCase("TONY STARK") == closures.formatLowerCaseClosure("Tony STark")
        
        //parameters
        assert closures.greet("Alex") == "Hello! Alex"
        
        assert closures.addAll(12, 10, 14) == 36
        
        def area = { length, breadth ->
            return length*breadth
        }
        
        assert closures.volume(10, area) == 160
       
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
        
        //closure vs lambda
        
        def players = ["A", "B", "C", "D", "E"]
        
        players.each {
            println it
        }
        
        //List<String> playerStream = players.stream().collect(Collectors.toList());
        //playerStream.forEach(System.out::println);
        
        //closure in GStrings
        def age = 12
        def gs = "Age is ${-> age}"
        assert gs == 'Age is 12'
        
        age = 24
        assert gs == "Age is 24"
        
        //closure in Lists
        def list = [10, 11, 12, 13, 14, true, false, "BUNTHER"]
        list.each {
            println it
        }
        
        assert [13, 14] == list.findAll{ it instanceof Integer && it >= 13}
       
        //closure in Maps
        def map = [1:10, 2:30, 4:5]
        
        assert [10, 60, 20] == map.collect{it.key * it.value}
        
    }
    
}