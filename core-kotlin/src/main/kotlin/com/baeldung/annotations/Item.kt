package com.baeldung
class Item(@Positive val amount: Float, @AllowedNames(["Alice", "Bob"]) val name: String)