package com.baeldung.kotlin.reflection

import org.junit.Test

class JavaReflectionTest {
    @Test
    fun listJavaClassMethods() {
        Exception::class.java.methods
                .forEach(::println)
    }

    @Test
    fun listKotlinClassMethods() {
        JavaReflectionTest::class.java.methods
                .forEach(::println)
    }

    @Test
    fun listKotlinDataClassMethods() {
        data class ExampleDataClass(val name: String, var enabled: Boolean)

        ExampleDataClass::class.java.methods
                .forEach(::println)
    }


}
