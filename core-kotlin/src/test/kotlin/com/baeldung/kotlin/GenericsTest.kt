package com.baeldung.kotlin

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GenericsTest {

    @Test
    fun givenParametrizeClass_whenInitializeItWithSpecificType_thenShouldBeParameterized() {
        //given
        val parameterizedClass = ParameterizedClass<String>("string-value")

        //when
        val res = parameterizedClass.getValue()

        //then
        assertTrue(res is String)
    }

    @Test
    fun givenParametrizeClass_whenInitializeIt_thenShouldBeParameterizedByInferredType() {
        //given
        val parameterizedClass = ParameterizedClass("string-value")

        //when
        val res = parameterizedClass.getValue()

        //then
        assertTrue(res is String)
    }

    @Test
    fun givenParameterizedProducerByOutKeyword_whenGetValue_thenCanAssignItToSuperType() {
        //given
        val parameterizedProducer = ParameterizedProducer("string")

        //when
        val ref: ParameterizedProducer<Any> = parameterizedProducer

        //then
        assertTrue(ref is ParameterizedProducer<Any>)
    }

    @Test
    fun givenParameterizedConsumerByInKeyword_whenGetValue_thenCanAssignItToSubType() {
        //given
        val parameterizedConsumer = ParameterizedConsumer<Number>()

        //when
        val ref: ParameterizedConsumer<Double> = parameterizedConsumer

        //then
        assertTrue(ref is ParameterizedConsumer<Double>)
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
        val listOfInts = listOf(5,2,3,4,1)

        //when
        val sorted = sort(listOfInts)

        //then
        assertEquals(sorted, listOf(1,2,3,4,5))
    }

    fun <T: Comparable<T>> sort(list: List<T>): List<T>{
        return list.sorted()
    }

    class ParameterizedClass<A>(private val value: A) {

        fun getValue(): A {
            return value
        }
    }

    class ParameterizedProducer<out T>(private val value: T) {
        fun get(): T {
            return value
        }
    }

    class ParameterizedConsumer<in T> {
        fun toString(value: T): String {
            return value.toString()
        }
    }
}
