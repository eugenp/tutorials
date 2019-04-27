package com.baeldung.kotlinvsjava

import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

class PropertiesTest {

    @Test
    fun givenASampleClassWithValAndVarProperties_whenSettingPrice_thenWeGetZeroOrOne() {
        val product = Product()
        product.price = BigDecimal(10)

        val product2 = Product()
        product2.price = null

        assertEquals("empty", product.id)
        assertEquals("empty", product2.id)
        assertEquals(BigDecimal(10), product.price)
        assertEquals(BigDecimal(1), product2.price)
    }

}

class Product {

    val id: String? = "empty"

    var price: BigDecimal? = BigDecimal.ZERO
        set(value) = if(value == null) { field = BigDecimal.ONE} else { field = value }

}