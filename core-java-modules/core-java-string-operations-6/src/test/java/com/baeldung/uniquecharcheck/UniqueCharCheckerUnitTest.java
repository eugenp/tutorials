package com.baeldung.uniquecharcheck;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class UniqueCharCheckerUnitTest {

    @Test
    public void givenUnique_whenBruteForceCheck_thenReturnTrue() {
        String[] sampleStrings = new String[]{"Justfewdi123", "$%&Hibusc", "Hibusc%$#", "მშვნიერ"};
        final String MSG = "Duplicate found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertTrue(MSG + " in " + sampleStr, UniqueCharChecker.bruteForceCheck(sampleStr)));
    }
    @Test
    public void givenUnique_whenSortAndThenCheck_thenReturnTrue() {
        String[] sampleStrings = new String[]{"Justfewdi123", "$%&Hibusc", "Hibusc%$#", "მშვნიერ"};
        final String MSG = "Duplicate found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertTrue(MSG + " in " + sampleStr, UniqueCharChecker.sortAndThenCheck(sampleStr)));
    }

    @Test
    public void givenUnique_whenUseSetCheck_thenReturnTrue() {
        String[] sampleStrings = new String[]{"Justfewdi123", "$%&Hibusc", "Hibusc%$#", "მშვნიერ"};
        final String MSG = "Duplicate found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertTrue(MSG + " in " + sampleStr, UniqueCharChecker.useSetCheck(sampleStr)));
    }

    @Test
    public void givenUnique_whenUseStreamCheck_thenReturnTrue() {
        String[] sampleStrings = new String[]{"Justfewdi123", "$%&Hibusc", "Hibusc%$#", "მშვნიერ"};
        final String MSG = "Duplicate found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertTrue(MSG + " in " + sampleStr, UniqueCharChecker.useStreamCheck(sampleStr)));
    }
    @Test
    public void givenUnique_whenUseStringUtilscheck_thenReturnTrue() {
        String[] sampleStrings = new String[]{"Justfewdi123", "$%&Hibusc", "Hibusc%$#", "მშვნიერ"};
        final String MSG = "Duplicate found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertTrue(MSG + " in " + sampleStr, UniqueCharChecker.useStringUtilscheck(sampleStr)));
    }

    @Test
    public void givenNotUnique_whenBruteForceCheck_thenReturnFalse() {
        String[] sampleStrings = new String[]{"Justfewdif123", "$%&Hibushc", "Hibusuc%$#", "Hi%busc%$#", "მშვენიერი"};
        final String MSG = "Duplicate not found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertFalse(MSG + " in " + sampleStr, UniqueCharChecker.bruteForceCheck(sampleStr)));
    }

    @Test
    public void givenNotUnique_whenSortAndThenCheck_thenReturnFalse() {
        String[] sampleStrings = new String[]{"Justfewdif123", "$%&Hibushc", "Hibusuc%$#", "Hi%busc%$#", "მშვენიერი"};
        final String MSG = "Duplicate not found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertFalse(MSG + " in " + sampleStr, UniqueCharChecker.sortAndThenCheck(sampleStr)));

    }

    @Test
    public void givenNotUnique_whenUseSetCheck_thenReturnFalse() {
        String[] sampleStrings = new String[]{"Justfewdif123", "$%&Hibushc", "Hibusuc%$#", "Hi%busc%$#", "მშვენიერი"};
        final String MSG = "Duplicate not found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertFalse(MSG + " in " + sampleStr, UniqueCharChecker.useSetCheck(sampleStr)));
    }

    @Test
    public void givenNotUnique_whenUseStreamCheck_thenReturnFalse() {
        String[] sampleStrings = new String[]{"Justfewdif123", "$%&Hibushc", "Hibusuc%$#", "Hi%busc%$#", "მშვენიერი"};
        final String MSG = "Duplicate not found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertFalse(MSG + " in " + sampleStr, UniqueCharChecker.useStreamCheck(sampleStr)));
    }

    @Test
    public void givenNotUnique_whenUseStringUtilscheck_thenReturnFalse() {
        String[] sampleStrings = new String[]{"Justfewdif123", "$%&Hibushc", "Hibusuc%$#", "Hi%busc%$#", "მშვენიერი"};
        final String MSG = "Duplicate not found";
        Arrays.stream(sampleStrings)
                .forEach(sampleStr -> assertFalse(MSG + " in " + sampleStr, UniqueCharChecker.useStringUtilscheck(sampleStr)));
    }

}
