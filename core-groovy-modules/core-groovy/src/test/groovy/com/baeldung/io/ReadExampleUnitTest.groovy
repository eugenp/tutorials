package com.baeldung.io

import static org.junit.Assert.*
import org.junit.Test

class ReadExampleUnitTest {

    @Test
    void whenUsingEachLine_thenCorrectLinesReturned() {
        def expectedList = [
            'First line of text', 
            'Second line of text', 
            'Third line of text', 
            'Fourth line of text']

        def lines = []

        new File('src/main/resources/ioInput.txt').eachLine { line ->
            lines.add(line)
        }
        assertEquals(expectedList, lines)
    }

    @Test
    void whenUsingReadEachLineWithLineNumber_thenCorrectLinesReturned() {
        def expectedList = [
            'Second line of text', 
            'Third line of text', 
            'Fourth line of text']

        def lineNoRange = 2..4
        def lines = []

        new File('src/main/resources/ioInput.txt').eachLine { line, lineNo ->
            if (lineNoRange.contains(lineNo)) {
                lines.add(line)
            }
        }
        assertEquals(expectedList, lines)
    }
    
    @Test
    void whenUsingReadEachLineWithLineNumberStartAtZero_thenCorrectLinesReturned() {
        def expectedList = [
            'Second line of text',
            'Third line of text',
            'Fourth line of text']

        def lineNoRange = 1..3
        def lines = []

        new File('src/main/resources/ioInput.txt').eachLine(0, { line, lineNo ->
            if (lineNoRange.contains(lineNo)) {
                lines.add(line)
            }
        })
        assertEquals(expectedList, lines)
    }

    @Test
    void whenUsingWithReader_thenLineCountReturned() {
        def expectedCount = 4
        def actualCount = 0
        new File('src/main/resources/ioInput.txt').withReader { reader ->
            while(reader.readLine()) {
                actualCount++
            }
        }
        assertEquals(expectedCount, actualCount)
    }

    @Test
    void whenUsingNewReader_thenOutputFileCreated() {
        def outputPath = 'src/main/resources/ioOut.txt'
        def reader = new File('src/main/resources/ioInput.txt').newReader()
        new File(outputPath).append(reader)
        reader.close()
        def ioOut = new File(outputPath)
        assertTrue(ioOut.exists())
        ioOut.delete()
    }

    @Test
    void whenUsingWithInputStream_thenCorrectBytesAreReturned() {
        def expectedLength = 1139
        byte[] data = []
        new File("src/main/resources/binaryExample.jpg").withInputStream { stream ->
            data = stream.getBytes()
        }
        assertEquals(expectedLength, data.length)
    }

    @Test
    void whenUsingNewInputStream_thenOutputFileCreated() {
        def outputPath = 'src/main/resources/binaryOut.jpg'
        def is = new File('src/main/resources/binaryExample.jpg').newInputStream()
        new File(outputPath).append(is)
        is.close()
        def ioOut = new File(outputPath)
        assertTrue(ioOut.exists())
        ioOut.delete()
    }

    @Test
    void whenUsingCollect_thenCorrectListIsReturned() {
        def expectedList = ['First line of text', 'Second line of text', 'Third line of text', 'Fourth line of text']

        def actualList = new File('src/main/resources/ioInput.txt').collect {it}
        assertEquals(expectedList, actualList)
    }

    @Test
    void whenUsingAsStringArray_thenCorrectArrayIsReturned() {
        String[] expectedArray = ['First line of text', 'Second line of text', 'Third line of text', 'Fourth line of text']

        def actualArray = new File('src/main/resources/ioInput.txt') as String[]
        assertArrayEquals(expectedArray, actualArray)
    }

    @Test
    void whenUsingText_thenCorrectStringIsReturned() {
        def ln = System.getProperty('line.separator')
        def expectedString = "First line of text${ln}Second line of text${ln}Third line of text${ln}Fourth line of text"
        def actualString = new File('src/main/resources/ioInput.txt').text
        assertEquals(expectedString.toString(), actualString)
    }

    @Test
    void whenUsingBytes_thenByteArrayIsReturned() {
        def expectedLength = 1139
        def contents = new File('src/main/resources/binaryExample.jpg').bytes
        assertEquals(expectedLength, contents.length)
    }
}
