package com.baeldung.interfaces

interface BaseInterface {
    fun someMethod(): String
}

interface FirstChildInterface : BaseInterface {
    override fun someMethod(): String {
        return("Hello, from someMethod in FirstChildInterface")
    }
}

interface SecondChildInterface : BaseInterface {
    override fun someMethod(): String {
        return("Hello, from someMethod in SecondChildInterface")
    }
}

class ChildClass : FirstChildInterface, SecondChildInterface {
    override fun someMethod(): String {
        return super<SecondChildInterface>.someMethod()
    }
}