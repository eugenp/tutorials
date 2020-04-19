package com.baeldung.trywithresource

import org.junit.Test
import java.beans.ExceptionListener
import java.beans.XMLEncoder
import java.io.*
import java.lang.Exception
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class UseTest {

    @Test
    fun givenCloseable_whenUseIsCalled_thenItIsClosed() {
        val stringWriter = StringWriter()
        val writer = BufferedWriter(stringWriter) //Using a BufferedWriter because after close() it throws.
        writer.use {
            assertEquals(writer, it)

            it.write("something")
        }
        try {
            writer.write("something else")

            fail("write() should have thrown an exception because the writer is closed.")
        } catch (e: IOException) {
            //Ok
        }

        assertEquals("something", stringWriter.toString())
    }

    @Test
    fun givenAutoCloseable_whenUseIsCalled_thenItIsClosed() {
        val baos = ByteArrayOutputStream()
        val encoder = XMLEncoder(PrintStream(baos)) //XMLEncoder is AutoCloseable but not Closeable.
                                                    //Here, we use a PrintStream because after close() it throws.
        encoder.exceptionListener = ThrowingExceptionListener()
        encoder.use {
            assertEquals(encoder, it)

            it.writeObject("something")
        }
        try {
            encoder.writeObject("something else")
            encoder.flush()

            fail("write() should have thrown an exception because the encoder is closed.")
        } catch (e: IOException) {
            //Ok
        }
    }

    @Test
    fun whenSimpleFormIsUsed_thenItWorks() {
        StringWriter().use { it.write("something") }
    }
}

class ThrowingExceptionListener : ExceptionListener {
    override fun exceptionThrown(e: Exception?) {
        if(e != null) {
            throw e
        }
    }
}