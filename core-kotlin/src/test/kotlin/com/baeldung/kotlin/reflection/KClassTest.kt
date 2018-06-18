package com.baeldung.kotlin.reflection

import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
import kotlin.reflect.full.*

class KClassTest {
    @Test
    fun testKClassDetails() {
        val stringClass = String::class
        Assert.assertEquals("kotlin.String", stringClass.qualifiedName)
        Assert.assertFalse(stringClass.isData)
        Assert.assertFalse(stringClass.isCompanion)
        Assert.assertFalse(stringClass.isAbstract)
        Assert.assertTrue(stringClass.isFinal)
        Assert.assertFalse(stringClass.isSealed)

        val listClass = List::class
        Assert.assertEquals("kotlin.collections.List", listClass.qualifiedName)
        Assert.assertFalse(listClass.isData)
        Assert.assertFalse(listClass.isCompanion)
        Assert.assertTrue(listClass.isAbstract)
        Assert.assertFalse(listClass.isFinal)
        Assert.assertFalse(listClass.isSealed)
    }

    @Test
    fun testGetRelated() {
        println(TestSubject::class.companionObject)
        println(TestSubject::class.companionObjectInstance)
        println(TestObject::class.objectInstance)

        Assert.assertSame(TestObject, TestObject::class.objectInstance)
    }

    @Test
    fun testNewInstance() {
        val listClass = ArrayList::class

        val list = listClass.createInstance()
        Assert.assertTrue(list is ArrayList)
    }

    @Test
    fun testMembers() {
        val bigDecimalClass = BigDecimal::class

        println(bigDecimalClass.constructors)
        println(bigDecimalClass.functions)
        println(bigDecimalClass.memberProperties)
        println(bigDecimalClass.memberExtensionFunctions)
    }
}

class TestSubject {
    companion object {
        val name = "TestSubject"
    }
}

object TestObject {
    val answer = 42
}
