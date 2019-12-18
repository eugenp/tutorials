package com.baeldung.range

import org.junit.Test
import kotlin.test.assertEquals

import com.baeldung.jvmannotations.*;

class DocumentTest {

    @Test
    fun testDefaultMethod() {

        val myDocument = TextDocument()
        val myTextDocument = XmlDocument(myDocument)

        assertEquals("text", myDocument.getType())
        assertEquals("text", myTextDocument.getType())
        assertEquals("document", myTextDocument.getTypeDefault())
    }
}