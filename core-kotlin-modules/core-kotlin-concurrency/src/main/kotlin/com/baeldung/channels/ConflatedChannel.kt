package com.baeldung.channels

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val basket = Channel<String>(CONFLATED)

    launch { // coroutine1
        val fruits = listOf("Apple", "Orange", "Banana")
        for (fruit in fruits) {
            println("coroutine1: Sending $fruit")
            basket.send(fruit)
        }
    }

    launch { // coroutine2
        println("coroutine2: Received ${basket.receive()}")
    }

    delay(2000)
    coroutineContext.cancelChildren()
}