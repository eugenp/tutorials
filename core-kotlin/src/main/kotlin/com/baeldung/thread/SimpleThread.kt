package com.baeldung.thread

class SimpleThread: Thread() {

    override fun run() {
        println("${Thread.currentThread()} has run.")
    }
}