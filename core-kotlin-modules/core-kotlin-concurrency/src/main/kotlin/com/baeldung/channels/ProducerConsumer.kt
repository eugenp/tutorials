package com.baeldung.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
fun CoroutineScope.produceFruits(): ReceiveChannel<String> = produce {
    val fruits = listOf("Apple", "Orange", "Apple")
    for (fruit in fruits) send(fruit)
}

@ExperimentalCoroutinesApi
fun main() = runBlocking {
    val fruitChannel = produceFruits()
    for (fruit in fruitChannel) {
        println(fruit)
    }
    println("End!")
}