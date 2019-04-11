package com.baeldung.kotlinvsjava

import org.junit.Test
import kotlin.test.assertEquals

class DelegationTest {

    @Test
    fun givenAClassWithDelegation_whenCallDelegatedMethod_thenWeGetAResultDefinedInPassedObject() {
        val car = Car(V6Engine())

        assertEquals("Vroom", car.makeSound())
    }

}

interface Engine {
    fun makeSound(): String
}

class V6Engine: Engine {
    override fun makeSound(): String {
        return "Vroom"
    }
}

class Car(e: Engine) : Engine by e