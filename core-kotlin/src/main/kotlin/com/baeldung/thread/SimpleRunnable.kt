package com.baeldung.thread

class SimpleRunnable: Runnable {

    override fun run() {
        println("${Thread.currentThread()} has run.")
    }
}