package com.baeldung.thread

import org.junit.jupiter.api.Test
import kotlin.concurrent.thread

class ThreadUnitTest {

    @Test
    fun whenCreateThread_thenRun() {

        val thread = SimpleThread()
        thread.start()
    }

    @Test
    fun whenCreateThreadWithRunnable_thenRun() {

        val threadWithRunnable = Thread(SimpleRunnable())
        threadWithRunnable.start()
    }

    @Test
    fun whenCreateThreadWithSAMConversions_thenRun() {

        val thread = Thread {
            println("${Thread.currentThread()} has run.")
        }
        thread.start()
    }

    @Test
    fun whenCreateThreadWithMethodExtension_thenRun() {

        thread(start = true) {
            println("${Thread.currentThread()} has run.")
        }
    }
}