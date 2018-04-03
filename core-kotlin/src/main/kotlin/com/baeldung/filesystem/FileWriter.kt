package com.baeldung.filesystem

import java.io.File

class FileWriter {

    fun writeFileUsingPrintWriter(fileName: String, fileContent: String) =
        File(fileName).printWriter().use { out -> out.println(fileContent) }

    fun writeFileUsingBufferedWriter(fileName: String, fileContent: String) =
        File(fileName).bufferedWriter().use { out -> out.write(fileContent) }

    fun writeFileDirectly(fileName: String, fileContent: String) =
        File(fileName).writeText(fileContent)

    fun writeFileDirectlyAsBytes(fileName: String, fileContent: String) =
            File(fileName).writeBytes(fileContent.toByteArray())

}