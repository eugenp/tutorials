package com.baeldung.kotlin

import org.junit.Test
import kotlin.test.assertEquals
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

    @Test
    fun givenTypeProjections_whenOperateOnTwoList_thenCanAcceptListOfSubtypes() {
        //given
        val ints: Array<Int> = arrayOf(1, 2, 3)
        val any: Array<Any?> = arrayOfNulls(3)

        //when
        copy(ints, any)

        //then
        assertEquals(any[0], 1)
        assertEquals(any[1], 2)
        assertEquals(any[2], 3)

    }

    fun copy(from: Array<out Any>, to: Array<Any?>) {
        assert(from.size == to.size)
        for (i in from.indices)
            to[i] = from[i]
    }

    @Test
    fun givenTypeProjection_whenHaveArrayOfIn_thenShouldAddElementsOfSubtypesToIt() {
        //given
        val objects: Array<Any?> = arrayOfNulls(1)

        //when
        fill(objects, 1)

        //then
        assertEquals(objects[0], 1)
    }

    fun fill(dest: Array<in Int>, value: Int) {
        dest[0] = value
    }

    @Test
    fun givenStartProjection_whenPassAnyType_thenCompile() {
        //given
        val array = arrayOf(1,2,3)

        //then
        printArray(array)

    }

    fun printArray(array: Array<*>) {
        array.forEach { println(it) }
    }

    @Test
    fun givenFunctionWithDefinedGenericConstraints_whenCallWithProperType_thenCompile(){
        //given
        val arrayOfInts = listOf(5,2,3,4,1)

        //when
        val sorted = sort(arrayOfInts)

        //then
        assertEquals(sorted[0], 1)
        assertEquals(sorted[1], 2)
        assertEquals(sorted[2], 3)
        assertEquals(sorted[3], 4)
        assertEquals(sorted[4], 5)

    }

    fun <T: Comparable<T>> sort(list: List<T>): List<T>{
        return list.sorted()
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
