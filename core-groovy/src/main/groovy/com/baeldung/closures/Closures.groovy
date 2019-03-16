package com.baeldung.closures

//import org.junit.Assert
//import org.junit.Test

class Closures {
    
    def printWelcome = {
        println "Welcome to Closures!"
    }
    
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
    
    def greet = {
        return "Hello! ${it}"
    }
   
    def multiply = { x, y ->
        return x*y
    }
    
    def calculate = {int x, int y, String operation ->
        
        //logging closure
        def logging = {
            println "Performing $it"
        }
        
        def result = 0    
        switch(operation) {
            case "ADD":
                logging("Addition")
                result = x+y
                break
            case "SUB":
                logging("Subtraction")
                result = x-y
                break
            case "MUL":
                logging("Multiplication")
                result = x*y
                break
            case "DIV":
                logging("Division")
                result = x/y
                break
        }
        return result
    }
    
    def addAll = { int... args ->
        return args.sum()
    }
    
    def volume(Closure areaCalculator, int... dimensions) {
        if(dimensions.size() == 3) {
            
            //consider dimension[0] = length, dimension[1] = breadth, dimension[2] = height
            //for cube and cuboid
            return areaCalculator(dimensions[0], dimensions[1]) * dimensions[2]
        } else if(dimensions.size() == 2) {
            
            //consider dimension[0] = radius, dimension[1] = height
            //for cylinder and cone
            return areaCalculator(dimensions[0]) * dimensions[1]
        } else if(dimensions.size() == 1) {
            
            //consider dimension[0] = radius
            //for sphere
            return areaCalculator(dimensions[0]) * dimensions[0]
        }
        
    }
     
}