package com.baeldung.gettestname;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class JUnit4SimpleTestNameUnitTest {

    @Rule
    public TestName name = new TestName();

    private static String sortCharacters(String s) {
        char[] charArray = s.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }

    @Test
    public void givenString_whenSort_thenVerifySortForString() {
        System.out.println("displayName = " + name.getMethodName());
        String s = "abc";
        assertEquals(s, sortCharacters("cba"));
    }
}
