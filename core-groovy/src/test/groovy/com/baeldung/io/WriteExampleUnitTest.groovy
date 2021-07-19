package com.baeldung.io

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class WriteExampleUnitTest {
    @Before
    void clearOutputFile() {
        new File('src/main/resources/ioOutput.txt').text = ''
        new File('src/main/resources/ioBinaryOutput.bin').delete()
    }
    
    @Test
    void whenUsingWithWriter_thenFileCreated() {
        def outputLines = [
            'Line one of output example',
            'Line two of output example',
            'Line three of output example'
        ]
        
        def outputFileName = 'src/main/resources/ioOutput.txt'
        new File(outputFileName).withWriter { writer ->
            outputLines.each { line ->
                writer.writeLine line
            }
        }
        def writtenLines = new File(outputFileName).collect {it}
        assertEquals(outputLines, writtenLines)
    }
    
    @Test
    void whenUsingNewWriter_thenFileCreated() {
        def outputLines = [
            'Line one of output example',
            'Line two of output example',
            'Line three of output example'
        ]
        
        def outputFileName = 'src/main/resources/ioOutput.txt'
        def writer = new File(outputFileName).newWriter()
        outputLines.forEach {line ->
            writer.writeLine line
        }
        writer.flush()
        writer.close()
        
        def writtenLines = new File(outputFileName).collect {it}
        assertEquals(outputLines, writtenLines)
    }
    
    @Test
    void whenUsingDoubleLessThanOperator_thenFileCreated() {
        def outputLines = [
            'Line one of output example',
            'Line two of output example',
            'Line three of output example'
        ]
        
        def ln = System.getProperty('line.separator')
        def outputFileName = 'src/main/resources/ioOutput.txt'
        new File(outputFileName) << "Line one of output example${ln}Line two of output example${ln}Line three of output example"
        def writtenLines = new File(outputFileName).collect {it}
        assertEquals(outputLines.size(), writtenLines.size())
    }
    
    @Test
    void whenUsingBytes_thenBinaryFileCreated() {
        def outputFileName = 'src/main/resources/ioBinaryOutput.bin'
        def outputFile = new File(outputFileName)
        byte[] outBytes = [44, 88, 22]
        outputFile.bytes = outBytes
        assertEquals(3, new File(outputFileName).size())
    }
    
    @Test
    void whenUsingWithOutputStream_thenBinaryFileCreated() {
        def outputFileName = 'src/main/resources/ioBinaryOutput.bin'
        byte[] outBytes = [44, 88, 22]
        new File(outputFileName).withOutputStream { stream ->
            stream.write(outBytes)
        }
        assertEquals(3, new File(outputFileName).size())
    }
    
    @Test
    void whenUsingNewOutputStream_thenBinaryFileCreated() {
        def outputFileName = 'src/main/resources/ioBinaryOutput.bin'
        byte[] outBytes = [44, 88, 22]
        def os = new File(outputFileName).newOutputStream()
        os.write(outBytes)
        os.close()
        assertEquals(3, new File(outputFileName).size())
    }
}
