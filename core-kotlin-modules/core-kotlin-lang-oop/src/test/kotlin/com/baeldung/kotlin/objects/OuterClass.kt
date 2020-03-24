package com.baeldung.kotlin.objects

class OuterClass {
    companion object {
        private val secret = "You can't see me"
        val public = "You can see me"
    }

    fun getSecretValue() = secret
}
