package com.baeldung.inputstream

import kotlinx.io.core.use
import org.junit.Test
import java.io.BufferedReader
import java.io.File
import kotlin.test.assertEquals

class InputStreamToStringTest {

    private val fileName = "src/test/resources/inputstream2string.txt"
    private val endOfLine = System.lineSeparator()
    private val fileFullContent = "Computer programming can be a hassle$endOfLine" +
            "It's like trying to take a defended castle"

    @Test
    fun whenReadFileWithBufferedReader_thenFullFileContentIsReadAsString() {
        val file = File(fileName)
        val inputStream = file.inputStream()
        val content = inputStream.bufferedReader().use(BufferedReader::readText)
        assertEquals(fileFullContent, content)
    }

    @Test
    fun whenReadFileWithBufferedReaderReadText_thenFullFileContentIsReadAsString() {
        val file = File(fileName)
        val inputStream = file.inputStream()
        val reader = BufferedReader(inputStream.reader())
        var content: String
        try {
            content = reader.readText()
        } finally {
            reader.close()
        }
        assertEquals(fileFullContent, content)
    }

    @Test
    fun whenReadFileWithBufferedReaderManually_thenFullFileContentIsReadAsString() {
        val file = File(fileName)
        val inputStream = file.inputStream()
        val reader = BufferedReader(inputStream.reader())
        val content = StringBuilder()
        try {
            var line = reader.readLine()
            while (line != null) {
                content.append(line)
                line = reader.readLine()
            }
        } finally {
            reader.close()
        }
        assertEquals(fileFullContent.replace(endOfLine, ""), content.toString())

    }

    @Test
    fun whenReadFileUpToStopChar_thenPartBeforeStopCharIsReadAsString() {
        val file = File(fileName)
        val inputStream = file.inputStream()
        val content = inputStream.use { it.readUpToChar(' ') }
        assertEquals("Computer", content)
    }

    @Test
    fun whenReadFileWithoutContainingStopChar_thenFullFileContentIsReadAsString() {
        val file = File(fileName)
        val inputStream = file.inputStream()
        val content = inputStream.use { it.readUpToChar('-') }
        assertEquals(fileFullContent, content)
    }

}

