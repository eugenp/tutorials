package com.baeldung.printwriterwritevsprint;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class WriteVsPrintUnitTest {

    Object outputFromPrintWriter;
    Object outputFromPrintWriter() {
        try (BufferedReader br = new BufferedReader(new FileReader("output.txt"))){
            outputFromPrintWriter = br.readLine();
        } catch (IOException e){
            e.printStackTrace();
            Assertions.fail();
        }
        return outputFromPrintWriter;
    }

    @Test
    void whenUsingWriteInt_thenASCIICharacterIsPrinted() throws FileNotFoundException {

        PrintWriter printWriter = new PrintWriter("output.txt");

        printWriter.write(48);
        printWriter.close();

        assertEquals("0", outputFromPrintWriter());
    }

    @Test
    void whenUsingWriteCharArrayFromOffset_thenCharArrayIsPrinted() throws FileNotFoundException {

        PrintWriter printWriter = new PrintWriter("output.txt");

        printWriter.write(new char[]{'A','/','&','4','E'}, 1, 4 );
        printWriter.close();

        assertEquals("/&4E", outputFromPrintWriter());
    }

    @Test
    void whenUsingWriteStringFromOffset_thenLengthOfStringIsPrinted() throws FileNotFoundException {

        PrintWriter printWriter = new PrintWriter("output.txt");

        printWriter.write("StringExample", 6, 7 );
        printWriter.close();

        assertEquals("Example", outputFromPrintWriter());
    }

    @Test
    void whenUsingPrintBoolean_thenStringValueIsPrinted() throws FileNotFoundException {

        PrintWriter printWriter = new PrintWriter("output.txt");

        printWriter.print(true);
        printWriter.close();

        assertEquals("true", outputFromPrintWriter());
    }

    @Test
    void whenUsingPrintChar_thenCharIsPrinted() throws FileNotFoundException {

        PrintWriter printWriter = new PrintWriter("output.txt");

        printWriter.print('A');
        printWriter.close();

        assertEquals("A", outputFromPrintWriter());
    }

    @Test
    void whenUsingPrintInt_thenValueOfIntIsPrinted() throws FileNotFoundException {

        PrintWriter printWriter = new PrintWriter("output.txt");

        printWriter.print(420);
        printWriter.close();

        assertEquals("420", outputFromPrintWriter());
    }

    @Test
    void whenUsingPrintString_thenStringIsPrinted() throws FileNotFoundException {

        PrintWriter printWriter = new PrintWriter("output.txt");

        printWriter.print("RandomString");
        printWriter.close();

        assertEquals("RandomString", outputFromPrintWriter());
    }

    @Test
    void whenUsingPrintObject_thenObjectToStringIsPrinted() throws FileNotFoundException {

        PrintWriter printWriter = new PrintWriter("output.txt");

        Map example = new HashMap();

        printWriter.print(example);
        printWriter.close();

        assertEquals(example.toString(), outputFromPrintWriter());
    }
}