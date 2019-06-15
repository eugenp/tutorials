package com.baeldung.jvmannotations

import java.util.*

interface Document {

    @JvmDefault
    fun getTypeDefault() = "document"

    fun getType() = "document"
}
