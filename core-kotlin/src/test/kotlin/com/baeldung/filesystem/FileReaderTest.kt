package com.baeldung.filesystem

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class FileReaderTest {

    private val fileName = "src/test/resources/Kotlin.in"

    private val fileReader = FileReader()

    @Test
    fun whenReadFileLineByLineUsingForEachLine_thenCorrect() {
        val lines = fileReader.readFileLineByLineUsingForEachLine(fileName)

        assertTrue { lines.contains("Hello to Kotlin. Its:") }
    }

    @Test
    fun whenReadFileAsLinesUsingUseLines_thenCorrect() {
        val lines = fileReader.readFileAsLinesUsingUseLines(fileName)

        assertTrue { lines.contains("1. Concise") }
    }

    @Test
    fun whenReadFileAsLinesUsingBufferedReader_thenCorrect() {
        val lines = fileReader.readFileAsLinesUsingBufferedReader(fileName)

        assertTrue { lines.contains("2. Safe") }
    }

    @Test
    fun whenReadFileAsLinesUsingReadLines_thenCorrect() {
        val lines = fileReader.readFileAsLinesUsingReadLines(fileName)

        assertTrue { lines.contains("3. Interoperable") }
    }

    @Test
    fun whenReadFileAsTextUsingInputStream_thenCorrect() {
        val text = fileReader.readFileAsTextUsingInputStream(fileName)

        assertTrue { text.contains("4. Tool-friendly") }
    }

    @Test
    fun whenReadDirectlyAsText_thenCorrect() {
        val text = fileReader.readFileDirectlyAsText(fileName)

        assertTrue { text.contains("Hello to Kotlin") }
    }
}