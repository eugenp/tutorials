package com.baeldung.testing.jgotesting;

import java.io.File;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import org.jgotesting.rule.JGoTestRule;
import static org.jgotesting.Assert.*; // same methods as org.junit.Assert.*
import static org.jgotesting.Check.*; // ditto, with different names
import static org.jgotesting.Testing.*;
import org.jgotesting.Checker;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class JGoTestingUnitTest {
    @Rule
    public final JGoTestRule test = new JGoTestRule();

    @Test
    public void whenComparingIntegers_thenEqual() {
        int anInt = 10;

        assertEquals(anInt, 10);
        checkEquals(anInt, 10);
    }

    @Ignore
    @Test
    public void whenComparingNumbers_thenLoggedMessage() {
        log("There was something wrong when comparing numbers");

        int anInt = 10;
        int anotherInt = 10;

        checkEquals(anInt, 10);
        checkTrue("First number should be bigger", 10 > anotherInt);
        checkSame(anInt, anotherInt);
    }

    @Ignore
    @Test
    public void whenComparingNumbers_thenFormattedMessage() {
        int anInt = 10;
        int anotherInt = 10;

        logf("There was something wrong when comparing numbers %d and %d", anInt, anotherInt);

        checkEquals(anInt, 10);
        checkTrue("First number should be bigger", 10 > anotherInt);
        checkSame(anInt, anotherInt);
    }

    @Test
    public void whenComparingStrings_thenMultipleAssertions() {
        String aString = "This is a string";
        String anotherString = "This Is A String";

        test.check(aString, equalToIgnoringCase(anotherString))
            .check(aString.length() == 16)
            .check(aString.startsWith("This"));
    }

    @Ignore
    @Test
    public void whenComparingStrings_thenMultipleFailingAssertions() {
        String aString = "the test string";
        String anotherString = "The Test String";

        checkEquals("Strings are not equal!", aString, anotherString);
        checkTrue("String is longer than one character", aString.length() == 1);
        checkSame("Strings are not the same", aString, anotherString);
    }

    @Ignore
    @Test
    public void givenFile_whenDoesnotExists_thenTerminated() throws Exception {
        File aFile = new File("a_dummy_file.txt");
        terminateIf(aFile.exists(), is(false));

        // This doesn't get executed
        checkEquals(aFile.getName(), "a_dummy_file.txt");
    }

    @Test
    public void givenChecker_whenComparingStrings_thenEqual() throws Exception {
        Checker<String> aChecker = s -> s.matches("\\d+");

        String aString = "1235";
        test.check(aString, aChecker);
    }

}

