package com.baeldung.kotlin.arrow

import arrow.core.*
import org.junit.Assert
import org.junit.Test

class FunctionalDataTypes {

    @Test
    fun whenIdCreated_thanValueIsPresent(){
        val id = Id("foo")
        val justId = Id.just("foo");

        Assert.assertEquals("foo", id.extract())
        Assert.assertEquals(justId, id)
    }

    fun length(s : String) : Int = s.length

    fun isBigEnough(i : Int) : Boolean = i > 10

    @Test
    fun whenIdCreated_thanMapIsAssociative(){
        val foo = Id("foo")

        val map1 = foo.map(::length)
                .map(::isBigEnough)
        val map2 = foo.map { s -> isBigEnough(length(s)) }

        Assert.assertEquals(map1, map2)
    }

    fun lengthId(s : String) : Id<Int> = Id.just(length(s))

    fun isBigEnoughId(i : Int) : Id<Boolean> = Id.just(isBigEnough(i))

    @Test
    fun whenIdCreated_thanFlatMapIsAssociative(){
        val bar = Id("bar")

        val flatMap = bar.flatMap(::lengthId)
            .flatMap(::isBigEnoughId)
        val flatMap1 = bar.flatMap { s -> lengthId(s).flatMap(::isBigEnoughId) }

        Assert.assertEquals(flatMap, flatMap1)
    }

    @Test
    fun whenOptionCreated_thanValueIsPresent(){
        val factory = Option.just(42)
        val constructor = Option(42)
        val emptyOptional = Option.empty<Integer>()
        val fromNullable = Option.fromNullable(null)

        Assert.assertEquals(42, factory.getOrElse { -1 })
        Assert.assertEquals(factory, constructor)
        Assert.assertEquals(emptyOptional, fromNullable)
    }

    @Test
    fun whenOptionCreated_thanConstructorDifferFromFactory(){
        val constructor : Option<String?> = Option(null)
        val fromNullable : Option<String?> = Option.fromNullable(null)

        try{
            constructor.map { s -> s!!.length }
            Assert.fail()
        } catch (e : KotlinNullPointerException){
            fromNullable.map { s->s!!.length }
        }
        Assert.assertNotEquals(constructor, fromNullable)
    }

    fun wrapper(x : Integer?) : Option<Int> = if (x == null) Option.just(-1) else Option.just(x.toInt())

    @Test
    fun whenOptionFromNullableCreated_thanItBreaksLeftIdentity(){
        val optionFromNull = Option.fromNullable(null)

        Assert.assertNotEquals(optionFromNull.flatMap(::wrapper), wrapper(null))
    }

    @Test
    fun whenEitherCreated_thanOneValueIsPresent(){
        val rightOnly : Either<String,Int> = Either.right(42)
        val leftOnly : Either<String,Int> = Either.left("foo")

        Assert.assertTrue(rightOnly.isRight())
        Assert.assertTrue(leftOnly.isLeft())
        Assert.assertEquals(42, rightOnly.getOrElse { -1 })
        Assert.assertEquals(-1, leftOnly.getOrElse { -1 })

        Assert.assertEquals(0, rightOnly.map { it % 2 }.getOrElse { -1 })
        Assert.assertEquals(-1, leftOnly.map { it % 2 }.getOrElse { -1 })
        Assert.assertTrue(rightOnly.flatMap { Either.Right(it % 2) }.isRight())
        Assert.assertTrue(leftOnly.flatMap { Either.Right(it % 2) }.isLeft())
    }

    @Test
    fun whenEvalNowUsed_thenMapEvaluatedLazily(){
        val now = Eval.now(1)
        Assert.assertEquals(1, now.value())

        var counter : Int = 0
        val map = now.map { x -> counter++; x+1 }
        Assert.assertEquals(0, counter)
        
        val value = map.value()
        Assert.assertEquals(2, value)
        Assert.assertEquals(1, counter)
    }

    @Test
    fun whenEvalLaterUsed_theResultIsMemoized(){
        var counter : Int = 0
        val later = Eval.later { counter++; counter }
        Assert.assertEquals(0, counter)

        val firstValue = later.value()
        Assert.assertEquals(1, firstValue)
        Assert.assertEquals(1, counter)

        val secondValue = later.value()
        Assert.assertEquals(1, secondValue)
        Assert.assertEquals(1, counter)
    }

    @Test
    fun whenEvalAlwaysUsed_theResultIsNotMemoized(){
        var counter : Int = 0
        val later = Eval.always { counter++; counter }
        Assert.assertEquals(0, counter)

        val firstValue = later.value()
        Assert.assertEquals(1, firstValue)
        Assert.assertEquals(1, counter)

        val secondValue = later.value()
        Assert.assertEquals(2, secondValue)
        Assert.assertEquals(2, counter)
    }

}