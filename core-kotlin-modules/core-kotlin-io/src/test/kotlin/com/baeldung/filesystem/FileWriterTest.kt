package com.baeldung.filesystem

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

internal class FileWriterTest {

    private val fileName = "src/test/resources/Kotlin.out"

    private val fileContent = "Kotlin\nConcise, Safe, Interoperable, Tool-friendly"

    private val fileWriter = FileWriter()

    @Test
    fun whenWrittenWithPrintWriter_thenCorrect() {
        fileWriter.writeFileUsingPrintWriter(fileName, fileContent)

        assertEquals(fileContent, File(fileName).readText())
    }

    @Test
    fun whenWrittenWithBufferedWriter_thenCorrect() {
        fileWriter.writeFileUsingBufferedWriter(fileName, fileContent)

        assertEquals(fileContent, File(fileName).readText())
    }

    @Test
    fun whenWrittenDirectly_thenCorrect() {
        fileWriter.writeFileDirectly(fileName, fileContent)

        assertEquals(fileContent, File(fileName).readText())
    }

    @Test
    fun whenWrittenDirectlyAsBytes_thenCorrect() {
        fileWriter.writeFileDirectlyAsBytes(fileName, fileContent)

        assertEquals(fileContent, File(fileName).readText())
    }

}