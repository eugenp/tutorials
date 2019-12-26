package com.baeldung.annotations

class Item(@Positive val amount: Float, @AllowedNames(["Alice", "Bob"]) val name: String)