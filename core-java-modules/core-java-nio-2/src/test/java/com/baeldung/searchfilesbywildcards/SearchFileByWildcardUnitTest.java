package com.baeldung.searchfilesbywildcards;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import java.nio.file.Paths;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SearchFileByWildcardUnitTest {
    @Test
    public void whenFourFilenameMatch_thenListOfFour() throws IOException 
    {
        SearchFileByWildcard sfbw = new SearchFileByWildcard();
        //List<String> actual = sfbw.searchWithWc(Paths.get("C:\\temp\\test"), "glob:*.{txt,docx}");
        //List<String> actual = sfbw.searchWithWc(Paths.get("C:\\dev\\java\\eclipseOxygen\\searchFilesByWildcards\\test\\resources\\test"), "glob:*.{txt,docx}");
        List<String> actual = sfbw.searchWithWc(Paths.get("src/test/resources/test"), "glob:*.{txt,docx}");
        
        assertEquals(new HashSet<>(actual).equals(new HashSet<>(Arrays.asList("six.txt", "three.txt", "two.docx", "one.txt"))), true);
    }
    @Test
    public void whenOneFilenameMatch_thenListOfOne() throws IOException 
    {
        SearchFileByWildcard sfbw = new SearchFileByWildcard();
        List<String> actual = sfbw.searchWithWc(Paths.get("src/test/resources/test"), "glob:????.{csv}");
        
        assertEquals(new HashSet<>(actual).equals(new HashSet<>(Arrays.asList("five.csv"))), true);
    }
}