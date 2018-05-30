package com.baeldung.kotlin.delegates

val data = arrayOf<MutableMap<String, Any?>>(
        mutableMapOf(
                "id" to 1,
                "name" to "George",
                "age" to 4
        ),
        mutableMapOf(
                "id" to 2,
                "name" to "Charlotte",
                "age" to 2
        )
)

class NoRecordFoundException(id: Int) : Exception("No record found for id $id") {
    init {
        println("No record found for ID $id")
    }
}

fun queryForValue(field: String, id: Int): Any {
    println("Loading record $id from the fake database")
    val value = data.firstOrNull { it["id"] == id }
            ?.get(field) ?: throw NoRecordFoundException(id)
    println("Loaded value $value for field $field of record $id")
    return value
}

fun update(field: String, id: Int, value: Any?) {
    println("Updating field $field of record $id to value $value in the fake database")
    data.firstOrNull { it["id"] == id }
            ?.put(field, value)
            ?: throw NoRecordFoundException(id)
}
