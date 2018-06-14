package com.baeldung.kotlin.reflection

import org.junit.Assert
import org.junit.Test
import java.io.ByteArrayInputStream
import java.nio.charset.Charset
import kotlin.reflect.*
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
        methodDetails(String::codePoints)
        methodDetails(String::byteInputStream)
    }

    private fun methodDetails(method: KFunction<*>) {
        println("Name: ${method.name}")
        println("Suspend: ${method.isSuspend}")
        println("External: ${method.isExternal}")
        println("Inline: ${method.isInline}")
        println("Operator: ${method.isOperator}")
    }

    val readOnlyProperty: Int = 42
    lateinit var mutableProperty: String

    @Test
    fun testPropertyDetails() {
        propertyDetails(this::readOnlyProperty)
        propertyDetails(this::mutableProperty)
    }

    private fun propertyDetails(field: KProperty<*>) {
        println("Name: ${field.name}")
        println("Late Init: ${field.isLateinit}")
        println("Const: ${field.isConst}")
        println("Mutable: ${field is KMutableProperty}")
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
