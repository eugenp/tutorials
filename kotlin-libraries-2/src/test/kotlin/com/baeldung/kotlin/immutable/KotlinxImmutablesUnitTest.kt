package com.baeldung.kotlin.immutable

import junit.framework.Assert.assertEquals
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class KotlinxImmutablesUnitTest{


  @Rule
  @JvmField
  var ee : ExpectedException = ExpectedException.none()

  @Test
  fun givenKICLList_whenAddTried_checkExceptionThrown(){

      val list: ImmutableList<String> = immutableListOf("I", "am", "immutable")

      list.add("My new item")

      assertEquals(listOf("I", "am", "immutable"), list)

  }
}