package com.baeldung.filesystem

import org.junit.jupiter.api.Test

internal class FileWriterTest {

    private val fileName = "src/test/resources/Kotlin.out"

    private val fileContent = "Kotlin\nConcise, Safe, Interoperable, Tool-friendly"

    private val fileWriter = FileWriter()

    @Test
    fun whenWrittenWithPrintWriter_thenCorrect() {
        fileWriter.writeFileUsingPrintWriter(fileName, fileContent)
    }

    @Test
    fun whenWrittenWithBufferedWriter_thenCorrect() {
        fileWriter.writeFileUsingBufferedWriter(fileName, fileContent)
    }

    @Test
    fun whenWrittenDirectly_thenCorrect() {
        fileWriter.writeFileDirectly(fileName, fileContent)
    }

    @Test
    fun whenWrittenDirectlyAsBytes_thenCorrect() {
        fileWriter.writeFileDirectlyAsBytes(fileName, fileContent)
    }

}