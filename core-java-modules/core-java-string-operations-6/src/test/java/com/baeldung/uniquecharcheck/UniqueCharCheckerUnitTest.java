package com.baeldung.uniquecharcheck;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class UniqueCharCheckerUnitTest {

    @Test
    public void givenMethCheck1_whenUnique_returnTrue() {
        String[] sampleStrings = new String[]{"Justfewdi123", "$%&Hibusc", "Hibusc%$#", "მშვნიერ"};
        final String MSG = "Duplicate found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertTrue(MSG + " in " + sampleStr, UniqueCharChecker.checkV1(sampleStr)));
    }
    @Test
    public void givenMethCheck2_whenUnique_returnTrue() {
        String[] sampleStrings = new String[]{"Justfewdi123", "$%&Hibusc", "Hibusc%$#", "მშვნიერ"};
        final String MSG = "Duplicate found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertTrue(MSG + " in " + sampleStr, UniqueCharChecker.checkV2(sampleStr)));
    }

    @Test
    public void givenMethCheck3_whenUnique_returnTrue() {
        String[] sampleStrings = new String[]{"Justfewdi123", "$%&Hibusc", "Hibusc%$#", "მშვნიერ"};
        final String MSG = "Duplicate found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertTrue(MSG + " in " + sampleStr, UniqueCharChecker.checkV3(sampleStr)));
    }

    @Test
    public void givenMethCheck4_whenUnique_returnTrue() {
        String[] sampleStrings = new String[]{"Justfewdi123", "$%&Hibusc", "Hibusc%$#", "მშვნიერ"};
        final String MSG = "Duplicate found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertTrue(MSG + " in " + sampleStr, UniqueCharChecker.checkV1(sampleStr)));
    }
    @Test
    public void givenMethCheck5_whenUnique_returnTrue() {
        String[] sampleStrings = new String[]{"Justfewdi123", "$%&Hibusc", "Hibusc%$#", "მშვნიერ"};
        final String MSG = "Duplicate found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertTrue(MSG + " in " + sampleStr, UniqueCharChecker.checkV5(sampleStr)));
    }

    @Test
    public void givenMethCheck1_whenNotUnique_returnFalse() {
        String[] sampleStrings = new String[]{"Justfewdif123", "$%&Hibushc", "Hibusuc%$#", "Hi%busc%$#", "მშვენიერი"};
        final String MSG = "Duplicate not found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertFalse(MSG + " in " + sampleStr, UniqueCharChecker.checkV1(sampleStr)));
    }

    @Test
    public void givenMethCheck2_whenNotUnique_returnFalse() {
        String[] sampleStrings = new String[]{"Justfewdif123", "$%&Hibushc", "Hibusuc%$#", "Hi%busc%$#", "მშვენიერი"};
        final String MSG = "Duplicate not found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertFalse(MSG + " in " + sampleStr, UniqueCharChecker.checkV2(sampleStr)));

    }

    @Test
    public void givenMethCheck3_whenNotUnique_returnFalse() {
        String[] sampleStrings = new String[]{"Justfewdif123", "$%&Hibushc", "Hibusuc%$#", "Hi%busc%$#", "მშვენიერი"};
        final String MSG = "Duplicate not found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertFalse(MSG + " in " + sampleStr, UniqueCharChecker.checkV3(sampleStr)));
    }

    @Test
    public void givenMethCheck4_whenNotUnique_returnFalse() {
        String[] sampleStrings = new String[]{"Justfewdif123", "$%&Hibushc", "Hibusuc%$#", "Hi%busc%$#", "მშვენიერი"};
        final String MSG = "Duplicate not found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertFalse(MSG + " in " + sampleStr, UniqueCharChecker.checkV4(sampleStr)));
    }

@Test
public void givenMethCheck5_whenNotUnique_returnFalse() {
    String[] sampleStrings = new String[]{"Justfewdif123", "$%&Hibushc", "Hibusuc%$#", "Hi%busc%$#", "მშვენიერი"};
    final String MSG = "Duplicate not found";
    Arrays.stream(sampleStrings)
            .forEach(sampleStr -> assertFalse(MSG + " in " + sampleStr, UniqueCharChecker.checkV5(sampleStr)));
}

}
