package com.baeldung.filesystem

import java.io.File

class FileReader {

    fun readFileLineByLineUsingForEachLine(fileName: String) = File(fileName).forEachLine { println(it) }

    fun readFileAsLinesUsingUseLines(fileName: String): List<String> = File(fileName)
      .useLines { it.toList() }

    fun readFileAsLinesUsingBufferedReader(fileName: String): List<String> = File(fileName).bufferedReader().readLines()

    fun readFileAsLinesUsingReadLines(fileName: String): List<String> = File(fileName).readLines()

    fun readFileAsTextUsingInputStream(fileName: String) =
      File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)

    fun readFileDirectlyAsText(fileName: String): String = File(fileName).readText(Charsets.UTF_8)

    fun readFileUsingGetResource(fileName: String) = this::class.java.getResource(fileName).readText(Charsets.UTF_8)

    fun readFileAsLinesUsingGetResourceAsStream(fileName: String) = this::class.java.getResourceAsStream(fileName).bufferedReader().readLines()
}