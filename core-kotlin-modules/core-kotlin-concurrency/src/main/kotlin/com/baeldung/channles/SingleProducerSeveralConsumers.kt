package com.baeldung.channles

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

@ExperimentalCoroutinesApi
fun CoroutineScope.producePizzaOrders(): ReceiveChannel<String> = produce {
    var x = 1
    while (true) {
        send("Pizza Order No. ${x++}")
        delay(100)
    }
}

fun CoroutineScope.pizzaOrderProcessor(id: Int, orders: ReceiveChannel<String>) = launch {
    for (order in orders) {
        println("Processor #$id is processing $order")
    }
}

@ExperimentalCoroutinesApi
fun main() = runBlocking {
    val pizzaOrders = producePizzaOrders()
    repeat(3) {
        pizzaOrderProcessor(it + 1, pizzaOrders)
    }

    delay(1000)
    pizzaOrders.cancel()
}