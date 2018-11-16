package com.baeldung.datamapping

data class User(
  val firstName: String,
  val lastName: String,
  val street: String,
  val houseNumber: String,
  val phone: String,
  val age: Int,
  val password: String)