package com.baeldung.file

import spock.lang.Specification

class ReadFileUnitTest extends Specification {

    ReadFile readFile

    void setup() {
        readFile = new ReadFile()
    }

    def 'Should return number of lines in File using ReadFile.readFileLineByLine given filePath'() {
        given:
        def filePath = "src/main/resources/fileContent.txt"

        when:
        def noOfLines = readFile.readFileLineByLine(filePath)

        then:
        noOfLines
        noOfLines instanceof Integer
        noOfLines == 3
    }

    def 'Should return File Content in list of lines using ReadFile.readFileInList given filePath'() {
        given:
        def filePath = "src/main/resources/fileContent.txt"

        when:
        def lines = readFile.readFileInList(filePath)

        then:
        lines
        lines instanceof List<String>
        lines.size() == 3
    }

    def 'Should return file content in string using ReadFile.readFileString given filePath'() {
        given:
        def filePath = "src/main/resources/fileContent.txt"

        when:
        def fileContent = readFile.readFileString(filePath)

        then:
        fileContent
        fileContent instanceof String
        fileContent.contains(["Line 1 : Hello World!!!", "Line 2 : This is a file content.", "Line 3 : String content"].join("\r\n"))
    }

    def 'Should return UTF-8 encoded file content in string using ReadFile.readFileStringWithCharset given filePath'() {
        given:
        def filePath = "src/main/resources/utf8Content.html"

        when:
        def encodedContent = readFile.readFileStringWithCharset(filePath)

        then:
        encodedContent
        encodedContent instanceof String
    }

    def 'Should return binary file content in byte array using ReadFile.readBinaryFile given filePath'() {
        given:
        def filePath = "src/main/resources/sample.png"

        when:
        def binaryContent = readFile.readBinaryFile(filePath)

        then:
        binaryContent
        binaryContent instanceof byte[]
        binaryContent.length == 329
    }
}
