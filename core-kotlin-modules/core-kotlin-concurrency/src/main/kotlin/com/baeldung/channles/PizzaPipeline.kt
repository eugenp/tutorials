package com.baeldung.channles

import com.baeldung.channles.OrderStatus.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

enum class OrderStatus { ORDERED, BAKED, TOPPED, SERVED }

data class PizzaOrder(val orderNumber: Int, val orderStatus: OrderStatus = ORDERED)

@ExperimentalCoroutinesApi
fun CoroutineScope.baking(orders: ReceiveChannel<PizzaOrder>) = produce {
    for (order in orders) {
        delay(200)
        println("Baking ${order.orderNumber}")
        send(order.copy(orderStatus = BAKED))
    }
}

@ExperimentalCoroutinesApi
fun CoroutineScope.topping(orders: ReceiveChannel<PizzaOrder>) = produce {
    for (order in orders) {
        delay(50)
        println("Topping ${order.orderNumber}")
        send(order.copy(orderStatus = TOPPED))
    }
}

@ExperimentalCoroutinesApi
fun CoroutineScope.produceOrders(count: Int) = produce {
    repeat(count) {
        delay(50)
        send(PizzaOrder(orderNumber = it + 1))
    }
}

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
fun main() = runBlocking {
    val orders = produceOrders(3)

    val readyOrders = topping(baking(orders))

    for (order in readyOrders) {
        println("Serving ${order.orderNumber}")
    }

    delay(3000)
    println("End!")
    coroutineContext.cancelChildren()
}