package com.baeldung.java.io.scanner;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.Test;

public class JavaScannerUnitTest {
    @Test
    public void whenReadingWords_thenCorrect() {
        String input = "Scanner Test";

        byte[] byteArray = input.getBytes(StandardCharsets.UTF_8);
        //@formatter:off
        try (InputStream is = new ByteArrayInputStream(byteArray); 
            Scanner scanner = new Scanner(is)) {

            //@formatter:on
            String result = scanner.next() + " " + scanner.next();

            assertEquals(input, result);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void whenReadingLines_thenCorrect() {
        String input = "Scanner\nTest\n";

        byte[] byteArray = input.getBytes(StandardCharsets.UTF_8);
        //@formatter:off
        try (InputStream is = new ByteArrayInputStream(byteArray); 
            Scanner scanner = new Scanner(is)) {

            //@formatter:on
            String result = scanner.nextLine() + " " + scanner.nextLine();

            String expected = input.replace("\n", " ")
                .trim();
            assertEquals(expected, result);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void whenReadingIntegers_thenCorrect() {
        String input = "100 200";

        byte[] byteArray = input.getBytes(StandardCharsets.UTF_8);
        //@formatter:off
        try (InputStream is = new ByteArrayInputStream(byteArray); 
            Scanner scanner = new Scanner(is)) {
           
            //@formatter:on
            String result = scanner.nextInt() + " " + scanner.nextInt();

            assertEquals(input, result);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void whenReadingIntegersWithCommaDelim_thenCorrect() {
        String input = "100,200,";

        byte[] byteArray = input.getBytes(StandardCharsets.UTF_8);
        //@formatter:off
        try (InputStream is = new ByteArrayInputStream(byteArray); 
            Scanner scanner = new Scanner(is)) {
            scanner.useDelimiter(",");
            
            //@formatter:on
            String result = scanner.nextInt() + " " + scanner.nextInt();

            String expected = input.replace(",", " ")
                .trim();
            assertEquals(expected, result);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void whenReadingBooleans_thenCorrect() {
        String input = "false true";

        byte[] byteArray = input.getBytes(StandardCharsets.UTF_8);
        //@formatter:off
        try (InputStream is = new ByteArrayInputStream(byteArray); 
            Scanner scanner = new Scanner(is)) {
            //@formatter:on

            String result = scanner.nextBoolean() + " " + scanner.nextBoolean();

            assertEquals(input, result);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void whenReadingIntegersWithLines_thenCorrect() {
        String input = "100\n200\n";

        byte[] byteArray = input.getBytes(StandardCharsets.UTF_8);
        //@formatter:off
        try (InputStream is = new ByteArrayInputStream(byteArray);
            Scanner scanner = new Scanner(is)) {
            
            String result = scanner.nextInt() 
                + scanner.nextLine()
                + " " 
                + scanner.nextInt()
                + scanner.nextLine();
            //@formatter:on

            String expected = input.replace("\n", " ")
                .trim();
            assertEquals(expected, result);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void whenParsingIntegersAsLines_thenCorrect() {
        String input = "100\n200\n";

        byte[] byteArray = input.getBytes(StandardCharsets.UTF_8);
        //@formatter:off
        try (InputStream is = new ByteArrayInputStream(byteArray);
            Scanner scanner = new Scanner(is)) {
            
            String result = Integer.parseInt(scanner.nextLine())
                + " " 
                + Integer.parseInt(scanner.nextLine());
            //@formatter:on

            String expected = input.replace("\n", " ")
                .trim();
            assertEquals(expected, result);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
