package com.baeldung.threadsvscoroutines

class SimpleThread: Thread() {

    override fun run() {
        println("${Thread.currentThread()} has run.")
    }
}