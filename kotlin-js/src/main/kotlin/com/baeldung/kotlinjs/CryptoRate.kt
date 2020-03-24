package com.baeldung.kotlinjs

external fun require(module: String): dynamic

data class CryptoCurrency(var name: String, var price: Float)

fun main(args: Array<String>) {
    println("Crypto Currency price API!")
    val express = require("express")
    val app = express()

    app.get("/crypto", { _, res ->
        res.send(generateCryptoRates())
    })

    app.listen(3000, {
        println("Listening on port 3000")
    })
}
fun generateCryptoRates(): Array<CryptoCurrency>{
    val cryptoCurrency = arrayOf<CryptoCurrency>(
            CryptoCurrency("Bitcoin", 90000F),
            CryptoCurrency("ETH",1000F),
            CryptoCurrency("TRX",10F)
    );
    return cryptoCurrency
}