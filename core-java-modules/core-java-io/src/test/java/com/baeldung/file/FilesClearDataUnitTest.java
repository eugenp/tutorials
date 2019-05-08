package com.baeldung.file;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.util.StreamUtils;

public class FilesClearDataUnitTest {
	
    public static final String FILE_PATH = "src/test/resources/fileexample.txt";

    @Before
    @After
    public void setup() throws IOException {
        PrintWriter writer = new PrintWriter(FILE_PATH);
        writer.print("This example shows how we can delete the file contents without deleting the file");
        writer.close();
    }
    
    @Test
    public void givenExistingFile_whenDeleteContentUsingPrintWritter_thenEmptyFile() throws IOException {
        PrintWriter writer = new PrintWriter(FILE_PATH);
        writer.print("");
        writer.close();
        assertEquals(0, StreamUtils.getStringFromInputStream(new FileInputStream(FILE_PATH)).length());
    }    

    @Test
    public void givenExistingFile_whenDeleteContentUsingPrintWritterWithougObject_thenEmptyFile() throws IOException {
        new PrintWriter(FILE_PATH).close();
        assertEquals(0, StreamUtils.getStringFromInputStream(new FileInputStream(FILE_PATH)).length());
    }    
       
    @Test
    public void givenExistingFile_whenDeleteContentUsingFileWriter_thenEmptyFile() throws IOException {
        new FileWriter(FILE_PATH, false).close();
 
        assertEquals(0, StreamUtils.getStringFromInputStream(new FileInputStream(FILE_PATH)).length());
    }
    
    @Test
    public void givenExistingFile_whenDeleteContentUsingFileOutputStream_thenEmptyFile() throws IOException {
        new FileOutputStream(FILE_PATH).close();

        assertEquals(0, StreamUtils.getStringFromInputStream(new FileInputStream(FILE_PATH)).length());
    }    

    @Test
    public void givenExistingFile_whenDeleteContentUsingFileUtils_thenEmptyFile() throws IOException {
        FileUtils.write(new File(FILE_PATH), "", Charset.defaultCharset());

        assertEquals(0, StreamUtils.getStringFromInputStream(new FileInputStream(FILE_PATH)).length());
    }    

    @Test
    public void givenExistingFile_whenDeleteContentUsingNIOFiles_thenEmptyFile() throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH));
        writer.write("");
        writer.flush();
 
        assertEquals(0, StreamUtils.getStringFromInputStream(new FileInputStream(FILE_PATH)).length());
    }    
    
    @Test
    public void givenExistingFile_whenDeleteContentUsingNIOFileChannel_thenEmptyFile() throws IOException {
        FileChannel.open(Paths.get(FILE_PATH), StandardOpenOption.WRITE).truncate(0).close();
 
        assertEquals(0, StreamUtils.getStringFromInputStream(new FileInputStream(FILE_PATH)).length());
    }   
    
    @Test
    public void givenExistingFile_whenDeleteContentUsingGuava_thenEmptyFile() throws IOException {
        File file = new File(FILE_PATH);
        byte[] empty = new byte[0];
        com.google.common.io.Files.write(empty, file);
        
        assertEquals(0, StreamUtils.getStringFromInputStream(new FileInputStream(FILE_PATH)).length());
    }    
}
