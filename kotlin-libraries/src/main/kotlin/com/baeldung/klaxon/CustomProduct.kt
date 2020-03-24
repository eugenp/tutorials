package com.baeldung.klaxon

import com.beust.klaxon.Json

class CustomProduct(
        @Json(name = "productName")
        val name: String,
        @Json(ignored = true)
        val id: Int)