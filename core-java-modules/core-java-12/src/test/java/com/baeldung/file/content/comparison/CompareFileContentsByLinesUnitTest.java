package com.baeldung.file.content.comparison;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CompareFileContentsByLinesUnitTest {

    public static Path path1 = null;
    public static Path path2 = null;
    
   @BeforeAll
    public static void setup()  throws IOException {
       
       path1 = Files.createTempFile("file1Test", ".txt");
       path2 = Files.createTempFile("file2Test", ".txt");
    }

   @Test
   public void whenFirstFileShorter_thenLineNumbersFirstFile() throws IOException {
       
       Files.writeString(path1, "testing line 1");
       Files.writeString(path2, "testing1 line 1" + System.lineSeparator() + "line 2");

       assertEquals(1, CompareFileContents.filesCompareByLine(path1, path2));
   }
   
   @Test
   public void whenSecondFileShorter_thenLineNumbersSecondFile() throws IOException {
       
       Files.writeString(path1, "testing1 line 1" + System.lineSeparator() + "line 2");
       Files.writeString(path2, "testing line 1");

       assertEquals(1, CompareFileContents.filesCompareByLine(path1, path2));
   }
   
   @Test
   public void whenFileIdentical_thenLineSuccess() throws IOException {
       
       Files.writeString(path1, "testing1 line 1" + System.lineSeparator() + "line 2");
       Files.writeString(path2, "testing1 line 1" + System.lineSeparator() + "line 2");

       assertEquals(-1, CompareFileContents.filesCompareByLine(path1, path2));
   }
   
   @Test
   public void whenFilesDifferent_thenLineNumber() throws IOException {
       
       Files.writeString(path1, "testing1 line 1" + System.lineSeparator() + "line 2");
       Files.writeString(path2, "testing1 line 1" + System.lineSeparator() + "linX 2");

       assertEquals(2, CompareFileContents.filesCompareByLine(path1, path2));
   }
 
   @Test
   public void whenBothFilesEmpty_thenEqual() throws IOException {
       
       Files.writeString(path1, "");
       Files.writeString(path2, "");

       assertEquals(-1, CompareFileContents.filesCompareByByte(path1, path2));
   }

   @Test
   public void whenFirstEmpty_thenPositionFirst() throws IOException {
       
       Files.writeString(path1, "");
       Files.writeString(path2, "testing1 line 1" + System.lineSeparator() + "line 2");

       assertEquals(1, CompareFileContents.filesCompareByByte(path1, path2));
   }

   @Test
   public void whenSecondEmpty_thenPositionFirst() throws IOException {
       
       Files.writeString(path1, "testing1 line 1" + System.lineSeparator() + "line 2");
       Files.writeString(path2, "");

       assertEquals(1, CompareFileContents.filesCompareByByte(path1, path2));
   }

    @AfterAll
    public static void shutDown() {
        
        path1.toFile().deleteOnExit();
        path2.toFile().deleteOnExit();
    }
}
