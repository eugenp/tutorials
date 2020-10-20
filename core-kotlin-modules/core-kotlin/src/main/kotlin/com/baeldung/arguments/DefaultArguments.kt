package com.baeldung.arguments

fun main() {

    // Skip both the connectTimeout and enableRetry arguments
    connect("http://www.baeldung.com")

    // Skip only the enableRetry argument:
    connect("http://www.baeldung.com", 5000)

    // Skip only the middle argument connectTimeout
    // connect("http://www.baeldung.com", false) // This results in a compiler error

    // Because we skipped the connectTimeout argument, we must pass the enableRetry as a named argument
    connect("http://www.baeldung.com", enableRetry = false)

    // Overriding Functions and Default Arguments
    val realConnector = RealConnector()
    realConnector.connect("www.baeldung.com")
    realConnector.connect()
}

fun connect(url: String, connectTimeout: Int = 1000, enableRetry: Boolean = true) {
    println("The parameters are url = $url, connectTimeout = $connectTimeout, enableRetry = $enableRetry")
}

open class AbstractConnector {
    open fun connect(url: String = "localhost") {
        // function implementation
    }
}

class RealConnector : AbstractConnector() {
    override fun connect(url: String) {
        println("The parameter is url = $url")
    }
}