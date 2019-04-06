package com.baeldung.inputstream

import kotlinx.io.core.use
import org.junit.Test
import java.io.BufferedReader
import java.io.File
import kotlin.test.assertEquals

class InputStreamToStringTest {
    private val fileName = "src/test/resources/inputstream2string.txt"
    private val fileFullContent = "Computer programming can be a hassle\r\n" +
            "It's like trying to take a defended castle"
    private val fileContentUpToNullChar = "Computer programming can be a hassle\r\n" +
            "It"

    @Test
    fun whenReadFileWithBufferedReader_thenFullFileContentIsReadAsString() {
        val file = File(fileName)
        val inputStream = file.inputStream()
        val content = inputStream.bufferedReader().use(BufferedReader::readText)
        assertEquals(fileFullContent, content)
    }

    @Test
    fun whenReadFileUpToNullChar_thenPartBeforeNullCharIsReadAsString() {
        val file = File(fileName)
        val inputStream = file.inputStream()
        val content = inputStream.use { it.readUpToNullChar('\'') }
        assertEquals(fileContentUpToNullChar, content)
    }

    @Test
    fun whenReadFileWithoutContainingNullChar_thenFullFileContentIsReadAsString() {
        val file = File(fileName)
        val inputStream = file.inputStream()
        val content = inputStream.use { it.readUpToNullChar('-') }
        assertEquals(fileFullContent, content)
    }


}

