package com.baeldung.allequalelements;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VerifyAllEqualListElementsUnitTest {

    private static List<String> distinctList = new ArrayList<>();

    static {
        distinctList.add(new String("Jack"));
        distinctList.add(new String("James"));
        distinctList.add(new String("Sam"));
    }

    private static List<String> notAllEqualList = new ArrayList<>();

    static {
        notAllEqualList.add(new String("Jack"));
        notAllEqualList.add(new String("James"));
        notAllEqualList.add(new String("Sam"));
        notAllEqualList.add(new String("James"));
    }

    private static List<String> emptyList = new ArrayList<>();

    private static List<String> allEqualList = new ArrayList<>();

    static {
        allEqualList.add(new String("Jack"));
        allEqualList.add(new String("Jack"));
        allEqualList.add(new String("Jack"));
        allEqualList.add(new String("Jack"));
    }

    private static VerifyAllEqualListElements verifyAllEqualListElements = new VerifyAllEqualListElements();

    @Test
    public void verifyAllEqualUsingALoop_whenUsingDistinctList_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingALoop(distinctList);

        assertFalse(allEqual);
    }

    @Test
    public void verifyAllEqualUsingALoop_whenNotAllEqualList_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingALoop(notAllEqualList);

        assertFalse(allEqual);
    }

    @Test
    public void verifyAllEqualUsingALoop_whenEmptyList_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingALoop(emptyList);

        assertTrue(allEqual);
    }

    @Test
    public void verifyAllEqualUsingALoop_whenAllEqualList_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingALoop(allEqualList);

        assertTrue(allEqual);
    }

    @Test
    public void verifyAllEqualUsingStream_whenUsingDistinctList_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingStream(distinctList);

        assertFalse(allEqual);
    }

    @Test
    public void verifyAllEqualUsingStream_whenNotAllEqualList_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingStream(notAllEqualList);

        assertFalse(allEqual);
    }

    @Test
    public void verifyAllEqualUsingStream_whenEmptyList_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingStream(emptyList);

        assertTrue(allEqual);
    }

    @Test
    public void verifyAllEqualUsingStream_whenAllEqualList_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingStream(allEqualList);

        assertTrue(allEqual);
    }

    @Test
    public void verifyAllEqualAnotherUsingStream_whenUsingDistinctList_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualAnotherUsingStream(distinctList);

        assertFalse(allEqual);
    }

    @Test
    public void verifyAllEqualAnotherUsingStream_whenNotAllEqualList_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualAnotherUsingStream(notAllEqualList);

        assertFalse(allEqual);
    }

    @Test
    public void verifyAllEqualAnotherUsingStream_whenEmptyList_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualAnotherUsingStream(emptyList);

        assertTrue(allEqual);
    }

    @Test
    public void verifyAllEqualAnotherUsingStream_whenAllEqualList_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualAnotherUsingStream(allEqualList);

        assertTrue(allEqual);
    }


    @Test
    public void verifyAllEqualUsingGuava_whenUsingDistinctList_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingGuava(distinctList);

        assertFalse(allEqual);
    }

    @Test
    public void verifyAllEqualUsingGuava_whenNotAllEqualList_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingGuava(notAllEqualList);

        assertFalse(allEqual);
    }

    @Test
    public void verifyAllEqualUsingGuava_whenEmptyList_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingGuava(emptyList);

        assertTrue(allEqual);
    }

    @Test
    public void verifyAllEqualUsingGuava_whenAllEqualList_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingGuava(allEqualList);

        assertTrue(allEqual);
    }

    @Test
    public void verifyAllEqualUsingApacheCommon_whenUsingDistinctList_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingApacheCommon(distinctList);

        assertFalse(allEqual);
    }

    @Test
    public void verifyAllEqualUsingApacheCommon_whenNotAllEqualList_thenReturnFalse() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingApacheCommon(notAllEqualList);

        assertFalse(allEqual);
    }

    @Test
    public void verifyAllEqualUsingApacheCommon_whenEmptyList_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingApacheCommon(emptyList);

        assertTrue(allEqual);
    }

    @Test
    public void verifyAllEqualUsingApacheCommon_whenAllEqualList_thenReturnTrue() {
        boolean allEqual = verifyAllEqualListElements.verifyAllEqualUsingApacheCommon(allEqualList);

        assertTrue(allEqual);
    }
}
