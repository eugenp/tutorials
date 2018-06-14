package com.baeldung.kotlin.reflection

import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
import kotlin.reflect.KClass
import kotlin.reflect.full.*

class KClassTest {
    @Test
    fun testKClassDetails() {
        classDetails(String::class)
        classDetails(List::class)
    }

    private fun classDetails(cls: KClass<*>) {
        println("Name: ${cls.qualifiedName}")
        println("Data class: ${cls.isData}")
        println("Companion class: ${cls.isCompanion}")
        println("Abstract class: ${cls.isAbstract}")
        println("Final class: ${cls.isFinal}")
        println("Sealed class: ${cls.isSealed}")
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
