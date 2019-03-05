package com.baeldung.closures

//import org.junit.Assert
//import org.junit.Test

class Closures {
    
    def print = { name ->
        println name
    }
    
    def formatLowerCase(name) {
        return name.toLowerCase()
    }
    def formatLowerCaseClosure = { name ->
        return name.toLowerCase()
    }
    
    def count=0
    
    def increaseCount = {
        count++
    }
    
    def stringUpperCase = { str ->
        str.toUpperCase()
    }
    
    def multiply = { int x, int y ->
        println x*y
        return x*y
    }
    
    def closureWithTypedParameters = { String x, int y, int z ->
        return "hey ${x} the value is ${y} and ${z}"
    }
    
    def printSelf = {
        println it
    }
    
    def printWithImplicitParameter = { it ->
        println it
    }
    
    def printWithExplicitParameter = { name ->
        println name
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
    
    def volume(height, areaClosure) {
        return height * areaClosure.call(2, 8)
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