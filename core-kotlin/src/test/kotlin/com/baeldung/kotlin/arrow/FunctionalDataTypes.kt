package com.baeldung.kotlin.arrow

import arrow.core.Id
import org.junit.Assert
import org.junit.Test

class FunctionalDataTypes {

    @Test
    fun whenIdCreated_valueIsPresent(){
        val const = Id("foo")
        val just = Id.just("foo");

        Assert.assertEquals("foo", const.extract())
        Assert.assertEquals(just, const)
    }

    fun length(s : String) : Int = s.length

    fun isBigEnough(i : Int) : Boolean = i > 10

    @Test
    fun whenIdCreated_mapIsAssociative(){
        val foo = Id("foo")

        val map1 = foo.map(::length)
                .map(::isBigEnough)
        val map2 = foo.map { s -> isBigEnough(length(s)) }

        Assert.assertEquals(map1, map2)
    }

    fun lengthId(s : String) : Id<Int> = Id.just(length(s))

    fun isBigEnoughId(i : Int) : Id<Boolean> = Id.just(isBigEnough(i))

    @Test
    fun whenIdCreated_flatMapIsAssociative(){
        val bar = Id("bar")

        val flatMap = bar.flatMap(::lengthId)
            .flatMap(::isBigEnoughId)
        val flatMap1 = bar.flatMap { s -> lengthId(s).flatMap(::isBigEnoughId) }

        Assert.assertEquals(flatMap, flatMap1)
    }

}