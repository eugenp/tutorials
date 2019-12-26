package com.baeldung.interfaces

interface MyInterface {
    fun someMethod(): String
}

class MyClass() : MyInterface {
    override fun someMethod(): String {
        return("Hello, World!")
    }
}

class MyDerivedClass(myInterface: MyInterface) : MyInterface by myInterface