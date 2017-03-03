package com.baeldung.kotlin

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WhenBlockUnitTest {

    @Test
    fun testWhenExpression() {
        val directoryType = UnixFileType.DIRECTORY

        val objectType = when (directoryType) {
            UnixFileType.DIRECTORY -> "d"
            UnixFileType.REGULAR_FILE -> "-"
            UnixFileType.SYMBOLIC_LINK -> "l"
        }

        assertEquals("d", objectType)
    }

    @Test
    fun testWhenExpressionWithDefaultCase() {
        val fileType = UnixFileType.SYMBOLIC_LINK

        val result = when (fileType) {
            UnixFileType.SYMBOLIC_LINK -> "linking to another file"
            else -> "not a link"
        }

        assertEquals("linking to another file", result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testWhenExpressionWithThrowException() {
        val fileType = UnixFileType.SYMBOLIC_LINK

        val result: Boolean = when(fileType) {
            UnixFileType.REGULAR_FILE -> true
            else -> throw IllegalArgumentException("Wrong type of file")
        }
    }

    @Test
    fun testWhenStatement() {
        val fileType = UnixFileType.REGULAR_FILE

        when (fileType) {
            UnixFileType.REGULAR_FILE -> println("Regular file type")
            UnixFileType.DIRECTORY -> println("Directory file type")
        }
    }

    @Test
    fun testCaseCombination() {
        val fileType = UnixFileType.DIRECTORY

        val frequentFileType: Boolean = when (fileType) {
            UnixFileType.REGULAR_FILE, UnixFileType.DIRECTORY -> true
            else -> false
        }

        assertTrue(frequentFileType)
    }

    @Test
    fun testWhenWithoutArgument() {
        val fileType = UnixFileType.SYMBOLIC_LINK

        val objectType = when {
            fileType === UnixFileType.SYMBOLIC_LINK -> "l"
            fileType === UnixFileType.REGULAR_FILE -> "-"
            fileType === UnixFileType.DIRECTORY -> "d"
            else -> "unknown file type"
        }

        assertEquals("l", objectType)
    }

    @Test
    fun testDynamicCaseExpression() {
        val unixFile = Directory(listOf())

        val unixFileType: UnixFileType = when (unixFile.getObjectType()) {
            "d" -> UnixFileType.DIRECTORY
            "-" -> UnixFileType.REGULAR_FILE
            "l" -> UnixFileType.SYMBOLIC_LINK
            else -> throw IllegalStateException()
        }

        assertEquals(UnixFileType.DIRECTORY, unixFileType)
    }

    @Test
    fun testRangeCollectionCaseExpressions() {
        val listOfFiles = listOf<UnixFile>(
                RegularFile("Test Content"),
                Directory(listOf()),
                SymbolicLink(RegularFile("Test Content"))
        )

        val regularFile = RegularFile("Test Content")



    }

    @Test
    fun testWhenWithIsOperatorWithSmartCase() {
        val unixFile: UnixFile = RegularFile("Test Content")

        val result = when (unixFile) {
            is RegularFile -> unixFile.content
            is Directory -> unixFile.children.map { it.getObjectType() }.joinToString()
            is SymbolicLink -> unixFile.originalFile.getObjectType()
        }

        assertEquals("Test Content", result)
    }

}