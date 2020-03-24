package com.baeldung.kotlin.objects

class StaticClass {
    companion object {
        @JvmStatic
        val staticField = 42
    }
}
