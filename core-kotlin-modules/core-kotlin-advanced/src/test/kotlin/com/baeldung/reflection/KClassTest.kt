package com.baeldung.reflection

import org.junit.Assert
import org.junit.Ignore
import org.junit.Test
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import kotlin.reflect.full.*

class KClassTest {
    private val LOG = LoggerFactory.getLogger(KClassTest::class.java)

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
        LOG.info("Companion Object: {}", TestSubject::class.companionObject)
        LOG.info("Companion Object Instance: {}", TestSubject::class.companionObjectInstance)
        LOG.info("Object Instance: {}", TestObject::class.objectInstance)

        Assert.assertSame(TestObject, TestObject::class.objectInstance)
    }

    @Test
    fun testNewInstance() {
        val listClass = ArrayList::class

        val list = listClass.createInstance()
        Assert.assertTrue(list is ArrayList)
    }

    @Test
    @Ignore
    fun testMembers() {
        val bigDecimalClass = BigDecimal::class

        LOG.info("Constructors: {}", bigDecimalClass.constructors)
        LOG.info("Functions: {}", bigDecimalClass.functions)
        LOG.info("Properties: {}", bigDecimalClass.memberProperties)
        LOG.info("Extension Functions: {}", bigDecimalClass.memberExtensionFunctions)
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
