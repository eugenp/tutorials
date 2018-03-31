package com.baeldung.filesystem

import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.nio.charset.StandardCharsets

class FileReader {

    fun readFileLineByLineUsingForEachLine(fileName: String): List<String> {
        val lineList = mutableListOf<String>()
        File(fileName).forEachLine { line -> lineList.add(line) }
        return lineList
    }

    fun readFileAsLinesUsingUseLines(fileName: String): List<String> {
        val lineList = mutableListOf<String>()
        File(fileName).useLines { lines -> lineList.addAll(lines) }
        return lineList
    }

    fun readFileAsLinesUsingBufferedReader(fileName: String): List<String> {
        val bufferedReader: BufferedReader = File(fileName).bufferedReader()
        return bufferedReader.readLines()
    }

    fun readFileAsLinesUsingReadLines(fileName: String): List<String> {
        return File(fileName).readLines(StandardCharsets.UTF_8)
    }

    fun readFileAsTextUsingInputStream(fileName: String): String {
        val inputStream: InputStream = File(fileName).inputStream()
        return inputStream.readBytes().toString(StandardCharsets.UTF_8)
    }

    fun readFileDirectlyAsText(fileName: String): String {
        return File(fileName).readText(StandardCharsets.UTF_8)
    }

}