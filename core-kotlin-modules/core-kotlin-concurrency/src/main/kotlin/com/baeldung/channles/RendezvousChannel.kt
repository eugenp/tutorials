package com.baeldung.channles

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main() = runBlocking {
    val basket = Channel<String>()

    launch { // coroutine1
        val fruits = listOf("Apple", "Orange", "Banana")
        for (fruit in fruits) {
            println("coroutine1: Sending $fruit")
            basket.send(fruit)
        }
    }

    launch { // coroutine2
        repeat(3) {
            delay(100)
            println("coroutine2: Received ${basket.receive()}")
        }
    }

    delay(2000)
    coroutineContext.cancelChildren()
}