package com.baeldung.kotlin

import org.junit.Test
import kotlin.test.assertTrue

class GenericsTest {

    @Test
    fun givenParametrizeClass_whenInitializeItWithSpecificType_thenShouldBeParametrized() {
        //given
        val parametrizedClass = ParametrizedClass<String>("string-value")

        //when
        val res = parametrizedClass.getValue()

        //then
        assertTrue(res is String)
    }

    @Test
    fun givenParametrizeClass_whenInitializeIt_thenShouldBeParametrizedByInferredType() {
        //given
        val parametrizedClass = ParametrizedClass("string-value")

        //when
        val res = parametrizedClass.getValue()

        //then
        assertTrue(res is String)
    }

    @Test
    fun givenParametrizedProducerByOutKeyword_whenGetValue_thenCanAssignItToSuperType() {
        //given
        val parametrizedProducer = ParametrizedProducer("string")

        //when
        val ref: ParametrizedProducer<Any> = parametrizedProducer

        //then
        assertTrue(ref is ParametrizedProducer<Any>)
    }

    @Test
    fun givenParametrizedConsumerByInKeyword_whenGetValue_thenCanAssignItToSubType() {
        //given
        val parametrizedConsumer = ParametrizedConsumer<Number>()

        //when
        val ref: ParametrizedConsumer<Double> = parametrizedConsumer

        //then
        assertTrue(ref is ParametrizedConsumer<Double>)
    }

    class ParametrizedClass<A>(private val value: A) {

        fun getValue(): A {
            return value
        }
    }

    class ParametrizedProducer<out T>(private val value: T) {
        fun get(): T {
            return value
        }
    }

    class ParametrizedConsumer<in T> {
        fun toString(value: T): String {
            return value.toString()
        }
    }
}
