package com.baeldung.builder

data class FoodOrderNamed(
  val bread: String? = null,
  val condiments: String? = null,
  val meat: String? = null,
  val fish: String? = null)
