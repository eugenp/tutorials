package com.baeldung.closures

import spock.lang.Specification

class ClosuresUnitTest extends GroovyTestCase {

    Closures closures = new Closures()
    
    void testClosures() {
        def x = 12
        def y = 10
        def multipliedResult = closures.multiplyWithReturn(x, y) // or closures.multiplyWithReturn.call(x,y)
        assert multipliedResult == 120
        
        assert closures.stringUpperCase("Hello! Closure") == "HELLO! CLOSURE"
        
        assert closures.stringUpperCaseWithExplicitType("I am closure") == "I AM CLOSURE"
        
        assert closures.addAll(12, 10, 14) == 36
        
        /*Delegation Strategy*/
        
        //this
        assert closures.self() instanceof Closures
        assert closures.nestedSelf() instanceof Closures
        
        //owner
        assert closures.self() == closures.classOwner()
        assert closures.classOwner() != closures.objectOwner()
        
        //delegate
        assert closures.self() == closures.ownerDelegate()
        assert closures.enclosedDelegate() == closures.enclosedDelegate
        
        //Set object to delegate
        def employee = new Employee(fullName: "Norman Lewis")
        closures.upperCaseFullName.delegate = employee
        assert closures.upperCaseFullName() == "NORMAN LEWIS"
        
        //closure in GStrings
        def age = 12
        def gs = "Age is ${-> age}"
        assert gs == 'Age is 12'
        
        age = 24
        assert gs == "Age is 24"
        
        //closure in Lists
        def list = [10, 11, 12, 13, 14, true, false, "Norman"]
        list.each {
            println it
        }
        
        assert [13, 14] == list.findAll{ it instanceof Integer && it >= 13}
       
        //closure in Maps
        def map = [1:10, 2:30, 4:5]
        
        assert [10, 60, 20] == map.collect{it.key * it.value}
        
        //SAM type coercion
        Human human = {"Norman"}
        assert human.greet() == "Hello, Norman"
        
        //interface coercion
        def impl = { println 'Kate'; 12 } as User
        
        assert impl.age() == 12
        impl.name()
        
        //left currying 
        def copyStr = { int numOfTimes, String str -> str*numOfTimes }
        def threeCopies = copyStr.curry(3)
        assert threeCopies('hello') == 'hellohellohello'
        assert threeCopies('bla') == copyStr(3, 'bla')
        
        //right currying
        def hellos = copyStr.rcurry('hello')
        assert hellos(2) == 'hellohello'
        assert hellos(2) == copyStr(2, 'hello')
        
        //index based currying
        def simpleInterest = { long amount, double interest, int years -> amount*interest*years/100 }
        def fixedTimeInterest = simpleInterest.ncurry(2, 10)
        
        assert simpleInterest(300L, 1.2d, 10) == fixedTimeInterest(300L, 1.2d)
        
        def fixedRateandTimeInterest = simpleInterest.ncurry(1, 1.2d, 10) //set multiple parameters, starting from the specified index
        assert simpleInterest(300L, 1.2d, 10) == fixedRateandTimeInterest(300L)
        
        //composition
        def add4  = { it + 4 }
        def multiply2 = { it * 2 }
        
        def multiply2add4 = add4 << multiply2
        assert multiply2add4(3) == 10
        assert multiply2add4(4) == add4(multiply2(4))
        
        def add4multiply2 = multiply2 << add4
        assert add4multiply2(10) == 28
        assert add4multiply2(15) == multiply2(add4(15))
        
        // reverse composition
        assert multiply2add4(3) == (multiply2 >> add4)(3)
        
        //trampoline
        def factorial
        factorial = { int num, BigInteger res = 1G ->
            if (num < 2) return res
            factorial.trampoline(num - 1, num * res)
        }
        factorial = factorial.trampoline()
        
        assert factorial(1) == 1
        assert factorial(6) == 1 * 2 * 3 * 4 * 5 * 6
        assert factorial(16) == 20922789888000
        assert factorial(50) == 30414093201713378043612608166064768844377641568960512000000000000
        
    }
    
}