package com.baeldung.closures

//import org.junit.Assert
//import org.junit.Test

class Closures {
    
    def print = { name ->
        println name
    }
    
    def formatToLowerCase(name) {
        return name.toLowerCase()
    }
    def formatToLowerCaseClosure = { name ->
        return name.toLowerCase()
    }
    
    def count=0
    
    def increaseCount = {
        count++
    }
    
    def stringUpperCase = { str ->
        str.toUpperCase()
    }
    
    def multiply = { x, y ->
        return x*y
    }
    
    def calculate = {int x, int y, String operation ->
        def result = 0    
        switch(operation) {
            case "ADD":
                result = x+y
                break
            case "SUB":
                result = x-y
                break
            case "MUL":
                result = x*y
                break
            case "DIV":
                result = x/y
                break
        }
        return result
    }
    
    def exchange = { float euros, String currency ->
        def euroRatesMap = ["RON":4.6, "USD":1.1]
        
        float exchangedAmount = euros
        
        if(euroRatesMap[currency]) {
            exchangedAmount = euros * euroRatesMap[currency]  
        }
        return exchangedAmount
    }
    
    def toggle = { it ->
        return !it
    }
    
    def greet = {
        return "Hello! ${it}"
    }
    
    def addAll = { int... args ->
        int result
        args.each {
            result += it
        }
        return result
    }
    
    def volume(Closure area, int... dimensions) {
        if(dimensions.size() == 3) {
            
            //consider dimension[0] = length, dimension[1] = breadth, dimension[2] = height
            //for cube and cuboid
            return area(dimensions[0], dimensions[1]) * dimensions[2]
        } else if(dimensions.size() == 2) {
            
            //consider dimension[0] = radius, dimension[1] = height
            //for cylinder and cone
            return area(dimensions[0]) * dimensions[1]
        } else if(dimensions.size() == 1) {
            
            //consider dimension[0] = radius
            //for sphere
            return area(dimensions[0]) * dimensions[0]
        }
        
    }
    
    def self = {
        return this
    }
    
    def nestedSelf = {
        def insideClosure = {
            return this
        }
        return insideClosure()
    }
    
    def classOwner = {
        return owner //returns class instance similar to this
    }
    
    def objectOwner = {
        def insideClosure = {
            return owner //returns object instance
        }
        return insideClosure()
    }
    
    def ownerDelegate = {
        return delegate //equivalent to this
    }
    
    def enclosedDelegate = {
        def insideClosure = {
            delegate
        }
        insideClosure.call()
    }
    
    def upperCaseFullName = {
        return delegate.fullName.toUpperCase()
    }
     
}