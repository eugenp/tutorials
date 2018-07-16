package com.baeldung.builder

fun main(args: Array<String>) {

    val order: FoodOrder = FoodOrder.Builder()
            .bread("white bread")
            .meat("bacon")
            .condiments("olive oil")
            .build()

    println("Order: ${order.bread}, ${order.meat}, ${order.condiments}")
}