package com.baeldung.reflection

import org.junit.Assert
import org.junit.Test
import java.io.ByteArrayInputStream
import java.nio.charset.Charset
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.starProjectedType

class KMethodTest {

    @Test
    fun testCallMethod() {
        val str = "Hello"
        val lengthMethod = str::length

        Assert.assertEquals(5, lengthMethod())
    }

    @Test
    fun testReturnType() {
        val str = "Hello"
        val method = str::byteInputStream

        Assert.assertEquals(ByteArrayInputStream::class.starProjectedType, method.returnType)
        Assert.assertFalse(method.returnType.isMarkedNullable)
    }

    @Test
    fun testParams() {
        val str = "Hello"
        val method = str::byteInputStream

        method.isSuspend
        Assert.assertEquals(1, method.parameters.size)
        Assert.assertTrue(method.parameters[0].isOptional)
        Assert.assertFalse(method.parameters[0].isVararg)
        Assert.assertEquals(Charset::class.starProjectedType, method.parameters[0].type)
    }

    @Test
    fun testMethodDetails() {
        val codePoints = String::codePoints
        Assert.assertEquals("codePoints", codePoints.name)
        Assert.assertFalse(codePoints.isSuspend)
        Assert.assertFalse(codePoints.isExternal)
        Assert.assertFalse(codePoints.isInline)
        Assert.assertFalse(codePoints.isOperator)

        val byteInputStream = String::byteInputStream
        Assert.assertEquals("byteInputStream", byteInputStream.name)
        Assert.assertFalse(byteInputStream.isSuspend)
        Assert.assertFalse(byteInputStream.isExternal)
        Assert.assertTrue(byteInputStream.isInline)
        Assert.assertFalse(byteInputStream.isOperator)
    }

    val readOnlyProperty: Int = 42
    lateinit var mutableProperty: String

    @Test
    fun testPropertyDetails() {
        val roProperty = this::readOnlyProperty
        Assert.assertEquals("readOnlyProperty", roProperty.name)
        Assert.assertFalse(roProperty.isLateinit)
        Assert.assertFalse(roProperty.isConst)
        Assert.assertFalse(roProperty is KMutableProperty<*>)

        val mProperty = this::mutableProperty
        Assert.assertEquals("mutableProperty", mProperty.name)
        Assert.assertTrue(mProperty.isLateinit)
        Assert.assertFalse(mProperty.isConst)
        Assert.assertTrue(mProperty is KMutableProperty<*>)
    }

    @Test
    fun testProperty() {
        val prop = this::mutableProperty

        Assert.assertEquals(String::class.starProjectedType, prop.getter.returnType)

        prop.set("Hello")
        Assert.assertEquals("Hello", prop.get())

        prop.setter("World")
        Assert.assertEquals("World", prop.getter())
    }
}
