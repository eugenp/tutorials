package com.baeldung.filesystem

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class FileReaderTest {

    private val fileName = "src/test/resources/Kotlin.in"

    private val fileReader = FileReader()

    @Test
    fun whenReadFileLineByLineUsingForEachLine_thenCorrect() {
        fileReader.readFileLineByLineUsingForEachLine(fileName)
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

    @Test
    fun whenReadFileAsTextUsingGetResource_thenCorrect() {
        val text = fileReader.readFileUsingGetResource("/Kotlin.in")

        assertTrue { text.contains("1. Concise") }
    }

    @Test
    fun whenReadFileUsingGetResourceAsStream_thenCorrect() {
        val lines = fileReader.readFileAsLinesUsingGetResourceAsStream("/Kotlin.in")

        assertTrue { lines.contains("3. Interoperable") }
    }


}