package com.baeldung.kotlinjs

import kotlin.js.Json

external fun require(module: String): dynamic

fun main(args: Array<String>) {
    println("Crypto Currency price API!")

    val express = require("express")
    val request = require("request");

    val app = express()

    //coin market api to fetch latest crypto currencies prices
    val url = "https://api.coinmarketcap.com/v2/ticker/?limit=10"

    app.get("/crypto", { _, res ->
        request(url) { error: String?, _, body: String ->
            res.type("application/json")
            if (error.isNullOrEmpty()) {
                val data: dynamic = JSON.parse<Json>(body)
                res.send(data);
            } else {
                println(error)
                res.send(error);
            }
        }

    })

    app.listen(3000, {
        println("Listening on port 3000")
    })
}