package com.baeldung.builder

data class FoodOrder(
        val bread: String? = null,
        val condiments: String? = null,
        val meat: String? = null,
        val fish: String? = null
)
