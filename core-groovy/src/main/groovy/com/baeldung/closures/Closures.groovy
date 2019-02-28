package com.baeldung.closures

//import org.junit.Assert
//import org.junit.Test

class Closures {
    
    def count=0
    
    def increaseCount = {
        count++
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
    
    def closureWithTypedParameters = { String x, int y, int z ->
        println "hey ${x} the value is ${y} and ${z}"
    }
    
    def multiplyWithReturn = { int x, int y ->
        println x*y
        return x*y
    }
    
    def stringUpperCase = { str -> 
        str.toUpperCase() 
    }
    
    def stringUpperCaseWithExplicitType = { String str -> 
        str.toUpperCase()
    }
    
    def greet = {
        println "Hello! ${it}"
    }
    
    def addAll = { int... args ->
        int result
        args.each {
            result += it
        }
        return result
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
        return delegate
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