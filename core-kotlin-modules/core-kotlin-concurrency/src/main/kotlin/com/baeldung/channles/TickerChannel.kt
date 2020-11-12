package com.baeldung.channles

import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.time.Duration
import kotlin.random.Random

fun stockPrice(stock: String): Double {
    log("Fetching stock price of $stock")
    return Random.nextDouble(2.0, 3.0)
}

fun main() = runBlocking {
    val tickerChannel = ticker(Duration.ofSeconds(5).toMillis())

    repeat(3) {
        tickerChannel.receive()
        log(stockPrice("TESLA"))
    }

    delay(Duration.ofSeconds(11).toMillis())
    tickerChannel.cancel()
}
