package com.baeldung.interfaces

interface SimpleInterface {
    val firstProp: String
    val secondProp: String
        get() = "Second Property"
    fun firstMethod(): String
    fun secondMethod(): String {
        println("Hello, from: " + secondProp)
        return ""
    }
}

class SimpleClass: SimpleInterface {
    override val firstProp: String = "First Property"
    override val secondProp: String
        get() = "Second Property, Overridden!"
    override fun firstMethod(): String {
        return("Hello, from: " + firstProp)
    }
    override fun secondMethod(): String {
        return("Hello, from: " + secondProp + firstProp)
    }
}