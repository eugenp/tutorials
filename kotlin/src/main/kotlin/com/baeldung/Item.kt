package com.baeldung

open class Item(val id: String, val name: String = "unknown_name")

class ItemWithCategory(id: String, name: String, val categoryId: String) : Item(id, name)

