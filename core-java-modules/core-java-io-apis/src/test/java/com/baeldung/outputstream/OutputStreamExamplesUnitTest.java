package com.baeldung.outputstream;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class OutputStreamExamplesUnitTest {
    
    StringBuilder filePath = new StringBuilder();
    
    @Before
    public void init() {
        filePath.append("src");
        filePath.append(File.separator);
        filePath.append("test");
        filePath.append(File.separator);
        filePath.append("resources");
        filePath.append(File.separator);
        filePath.append("output_file.txt");
    }
    
    @Test
    public void givenOutputStream_whenWriteSingleByteCalled_thenOutputCreated() throws IOException {
        
        final File file = new File(filePath.toString());    
        OutputStreamExamples examples = new OutputStreamExamples();    
        examples.fileOutputStreamByteSingle(filePath.toString(), "Hello World!");
        assertTrue(file.exists());
        file.delete();
    }
    
    @Test
    public void givenOutputStream_whenWriteByteSequenceCalled_thenOutputCreated() throws IOException {
        
        final File file = new File(filePath.toString());    
        OutputStreamExamples examples = new OutputStreamExamples();    
        examples.fileOutputStreamByteSequence(filePath.toString(), "Hello World!");
        assertTrue(file.exists());
        file.delete();
    }
    
    @Test
    public void givenOutputStream_whenWriteByteSubSequenceCalled_thenOutputCreated() throws IOException {
        
        final File file = new File(filePath.toString());    
        OutputStreamExamples examples = new OutputStreamExamples();    
        examples.fileOutputStreamByteSubSequence(filePath.toString(), "Hello World!");
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void givenBufferedOutputStream_whenCalled_thenOutputCreated() throws IOException {
        
        final File file = new File(filePath.toString());    
        OutputStreamExamples examples = new OutputStreamExamples();    
        examples.bufferedOutputStream(filePath.toString(), "Hello", "World!");
        assertTrue(file.exists());
        file.delete();
    }
    
    @Test
    public void givenOutputStreamWriter_whenCalled_thenOutputCreated() throws IOException {
        
        final File file = new File(filePath.toString());    
        OutputStreamExamples examples = new OutputStreamExamples();    
        examples.outputStreamWriter(filePath.toString(), "Hello World!");
        assertTrue(file.exists());
        file.delete();
    }

}
