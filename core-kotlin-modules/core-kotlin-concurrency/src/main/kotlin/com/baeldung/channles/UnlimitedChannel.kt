package com.baeldung.channles

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val channel = Channel<Int>(UNLIMITED)

    launch { // coroutine1
        repeat(100) {
            println("coroutine1: Sending $it")
            channel.send(it)
        }
    }

    launch { // coroutine2
        repeat(100) {
            println("coroutine2: Received ${channel.receive()}")
        }
    }

    delay(2000)
    coroutineContext.cancelChildren()
}