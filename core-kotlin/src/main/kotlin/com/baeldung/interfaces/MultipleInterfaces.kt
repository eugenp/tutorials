package com.baeldung.interfaces

interface FirstInterface {	
    fun someMethod(): String
	
    fun anotherMethod(): String {
        return("Hello, from anotherMethod in FirstInterface")
    }
}

interface SecondInterface {
    fun someMethod(): String {
        return("Hello, from someMethod in SecondInterface")
    }
	
    fun anotherMethod(): String {
        return("Hello, from anotherMethod in SecondInterface")
    }
}

class SomeClass: FirstInterface, SecondInterface {	
    override fun someMethod(): String {
        return("Hello, from someMethod in SomeClass")
    }
	
    override fun anotherMethod(): String {
        return("Hello, from anotherMethod in SomeClass")
    }
}