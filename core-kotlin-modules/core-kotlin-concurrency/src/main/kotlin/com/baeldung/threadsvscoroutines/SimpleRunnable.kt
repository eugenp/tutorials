package com.baeldung.threadsvscoroutines

class SimpleRunnable: Runnable {

    override fun run() {
        println("${Thread.currentThread()} has run.")
    }
}