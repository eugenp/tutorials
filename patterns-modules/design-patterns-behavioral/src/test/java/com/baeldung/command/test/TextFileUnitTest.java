package com.baeldung.command.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.command.receiver.TextFile;

public class TextFileUnitTest {
    
    private static TextFile textFile;
    
    
    @BeforeClass
    public static void setUpTextFileInstance() {
        textFile = new TextFile("file1.txt");
    }
    
    @Test
    public void givenTextFileInstance_whenCalledopenMethod_thenOneAssertion() {
        assertThat(textFile.open()).isEqualTo("Opening file file1.txt");
    }
    
    @Test
    public void givenTextFileInstance_whenCalledwriteMethod_thenOneAssertion() {
        assertThat(textFile.write()).isEqualTo("Writing to file file1.txt");
    }
    
    @Test
    public void givenTextFileInstance_whenCalledsaveMethod_thenOneAssertion() {
        assertThat(textFile.save()).isEqualTo("Saving file file1.txt");
    }
    
    @Test
    public void givenTextFileInstance_whenCalledcopyMethod_thenOneAssertion() {
        assertThat(textFile.copy()).isEqualTo("Copying file file1.txt");
    }
    
    @Test
    public void givenTextFileInstance_whenCalledpasteMethod_thenOneAssertion() {
        assertThat(textFile.paste()).isEqualTo("Pasting file file1.txt");
    }
}
