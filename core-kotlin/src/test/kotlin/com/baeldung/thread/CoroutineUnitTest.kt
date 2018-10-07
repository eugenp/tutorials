package com.baeldung.thread

import kotlinx.coroutines.experimental.*
import org.junit.jupiter.api.Test

class CoroutineUnitTest {

    @Test
    fun whenCreateLaunchCoroutineWithoutContext_thenRun() {

        val job = launch {
            println("${Thread.currentThread()} has run.")
        }

        runBlocking {
            job.join()
        }
    }

    @Test
    fun whenCreateLaunchCoroutineWithDefaultContext_thenRun() {

        val job = launch(DefaultDispatcher) {
            println("${Thread.currentThread()} has run.")
        }

        runBlocking {
            job.join()
        }
    }

    @Test
    fun whenCreateLaunchCoroutineWithUnconfinedContext_thenRun() {

        val job = launch(Unconfined) {
            println("${Thread.currentThread()} has run.")
        }

        runBlocking {
            job.join()
        }
    }

    @Test
    fun whenCreateLaunchCoroutineWithDedicatedThread_thenRun() {

        val job = launch(newSingleThreadContext("dedicatedThread")) {
            println("${Thread.currentThread()} has run.")
        }

        runBlocking {
            job.join()
        }
    }

    @Test
    fun whenCreateAsyncCoroutine_thenRun() {

        val deferred = async(Unconfined) {
            return@async "${Thread.currentThread()} has run."
        }

        runBlocking {
            val result = deferred.await()
            println(result)
        }
    }
}