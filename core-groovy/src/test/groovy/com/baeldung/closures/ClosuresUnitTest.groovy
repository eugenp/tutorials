package com.baeldung.closures

import spock.lang.Specification

class ClosuresUnitTest extends GroovyTestCase {

    Closures closures = new Closures()
    
    void testDeclaration() {
        
        closures.print("Hello! Closure")
        closures.formatToLowerCaseClosure("Hello! Closure")
        
        closures.print.call("Hello! Closure")
        closures.formatToLowerCaseClosure.call("Hello! Closure")
        
    }
    
    void testClosureVsMethods() {   
        assert closures.formatToLowerCase("TONY STARK") == closures.formatToLowerCaseClosure("Tony STark")
    }
    
    void testParameters() {
        //implicit parameter
        assert closures.greet("Alex") == "Hello! Alex"
        
        //multiple parameters
        assert closures.multiply(2, 4) == 8
        
        assert closures.calculate(12, 4, "ADD") == 16
        assert closures.calculate(12, 4, "SUB") == 8
        assert closures.calculate(43, 8, "DIV") == 5.375
                
        //varags
        assert closures.addAll(12, 10, 14) == 36
       
    }
    
    void testClosureAsAnArgument() {
        assert closures.volume({ l, b -> return l*b }, 12, 6, 10) == 720
        
        assert closures.volume({ radius -> return Math.PI*radius*radius/3 }, 5, 10) == Math.PI * 250/3
    }
    
    void testGStringsLazyEvaluation() {
        def name = "Samwell"
        def welcomeMsg = "Welcome! $name"
        
        assert welcomeMsg == "Welcome! Samwell"
        
        // changing the name does not affect original interpolated value
        name = "Tarly"
        assert welcomeMsg != "Welcome! Tarly"
        
        def fullName = "Tarly Samson"
        def greetStr = "Hello! ${-> fullName}"
        
        assert greetStr == "Hello! Tarly Samson"

        // this time changing the variable affects the interpolated String's value
        fullName = "Jon Smith"
        assert greetStr == "Hello! Jon Smith"
    }
    
    void testClosureInLists() {
        def list = [10, 11, 12, 13, 14, true, false, "BUNTHER"]
        list.each {
            println it
        }
        
        assert [13, 14] == list.findAll{ it instanceof Integer && it >= 13}
    }
    
    void testClosureInMaps() {
        def map = [1:10, 2:30, 4:5]
        
        assert [10, 60, 20] == map.collect{it.key * it.value}
    }
    
}