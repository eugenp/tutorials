package com.baeldung.allequalelements;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VerifyAllEqualListElementsUnitTest {

    private static List<String> notAllEqualList = new ArrayList<>();

    private static List<String> emptyList = new ArrayList<>();

    private static List<String> allEqualList = new ArrayList<>();

    static {
        notAllEqualList = Arrays.asList("Jack", "James", "Sam", "James");
        emptyList = Arrays.asList();
        allEqualList = Arrays.asList("Jack", "Jack", "Jack", "Jack");
    }

    private static VerifyAllEqualListElements verifyAllEqualListElements = new VerifyAllEqualListElements();

    @Test
    public void givenNotAllEqualList_whenUsingALoop_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingALoop(notAllEqualList);

        assertFalse(allEqual);
    }

    @Test
    public void givenEmptyList_whenUsingALoop_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingALoop(emptyList);

        assertTrue(allEqual);
    }

    @Test
    public void givenAllEqualList_whenUsingALoop_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingALoop(allEqualList);

        assertTrue(allEqual);
    }

    @Test
    public void givenNotAllEqualList_whenUsingHashSet_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingHashSet(notAllEqualList);

        assertFalse(allEqual);
    }

    @Test
    public void givenEmptyList_whenUsingHashSet_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingHashSet(emptyList);

        assertTrue(allEqual);
    }

    @Test
    public void givenAllEqualList_whenUsingHashSet_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingHashSet(allEqualList);

        assertTrue(allEqual);
    }

    @Test
    public void givenNotAllEqualList_whenUsingFrequency_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingFrequency(notAllEqualList);

        assertFalse(allEqual);
    }

    @Test
    public void givenEmptyList_whenUsingFrequency_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingFrequency(emptyList);

        assertTrue(allEqual);
    }

    @Test
    public void givenAllEqualList_whenUsingFrequency_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingFrequency(allEqualList);

        assertTrue(allEqual);
    }

    @Test
    public void givenNotAllEqualList_whenUsingStream_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingStream(notAllEqualList);

        assertFalse(allEqual);
    }

    @Test
    public void givenEmptyList_whenUsingStream_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingStream(emptyList);

        assertTrue(allEqual);
    }

    @Test
    public void givenAllEqualList_whenUsingStream_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingStream(allEqualList);

        assertTrue(allEqual);
    }

    @Test
    public void givenNotAllEqualList_whenUsingAnotherStream_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualAnotherUsingStream(notAllEqualList);

        assertFalse(allEqual);
    }

    @Test
    public void givenEmptyList_whenUsingAnotherStream_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualAnotherUsingStream(emptyList);

        assertTrue(allEqual);
    }

    @Test
    public void givenAllEqualList_whenUsingAnotherStream_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualAnotherUsingStream(allEqualList);

        assertTrue(allEqual);
    }

    @Test
    public void givenNotAllEqualList_whenUsingGuava_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingGuava(notAllEqualList);

        assertFalse(allEqual);
    }

    @Test
    public void givenEmptyList_whenUsingGuava_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingGuava(emptyList);

        assertTrue(allEqual);
    }

    @Test
    public void givenAllEqualList_whenUsingGuava_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingGuava(allEqualList);

        assertTrue(allEqual);
    }

    @Test
    public void givenNotAllEqualList_whenUsingApacheCommon_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingApacheCommon(notAllEqualList);

        assertFalse(allEqual);
    }

    @Test
    public void givenEmptyList_whenUsingApacheCommon_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingApacheCommon(emptyList);

        assertTrue(allEqual);
    }

    @Test
    public void givenAllEqualList_whenUsingApacheCommon_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingApacheCommon(allEqualList);

        assertTrue(allEqual);
    }
}
