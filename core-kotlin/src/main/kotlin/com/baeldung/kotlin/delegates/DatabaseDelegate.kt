package com.baeldung.kotlin.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class DatabaseDelegate<in R, T>(private val field: String, private val id: Int) : ReadWriteProperty<R, T> {
    override fun getValue(thisRef: R, property: KProperty<*>): T =
            queryForValue(field, id) as T

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        update(field, id, value)
    }
}
