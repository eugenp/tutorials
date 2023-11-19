package com.baeldung.keyword;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class InstanceOfUnitTest {

    @Test
    void giveWhenInstanceIsCorrect_thenReturnTrue() {
        Ring ring = new Ring();
        assertTrue(ring instanceof Round);
    }

    @Test
    void giveWhenObjectIsInstanceOfType_thenReturnTrue() {
        Circle circle = new Circle();
        assertTrue(circle instanceof Circle);
    }


    @Test
    void giveWhenInstanceIsOfSubtype_thenReturnTrue() {
        Circle circle = new Circle();
        assertTrue(circle instanceof Round);
    }

    @Test
    void giveWhenTypeIsInterface_thenReturnTrue() {
        Circle circle = new Circle();
        assertTrue(circle instanceof Shape);
    }

    @Test
    void giveWhenTypeIsOfObjectType_thenReturnTrue() {
        Thread thread = new Thread();
        assertTrue(thread instanceof Object);
    }

    @Test
    void giveWhenInstanceValueIsNull_thenReturnFalse() {
        Circle circle = null;
        assertFalse(circle instanceof Round);
    }

    @Test
    void giveWhenComparingClassInDiffHierarchy_thenCompilationError() {
        //assertFalse( circle instanceof Triangle);
    }

    @Test
    void giveWhenStream_whenCastWithoutInstanceOfChk_thenGetException() {
        Stream<Round> roundStream = Stream.of(new Ring(), new Ring(), new Circle());
        assertThrows(ClassCastException.class, () -> roundStream.map(it -> (Ring) it).collect(Collectors.toList()));
    }

    @Test
    void giveWhenStream_whenCastAfterInstanceOfChk_thenGetExpectedResult() {
        Stream<Round> roundStream = Stream.of(new Ring(), new Ring(), new Circle());
        List<Ring> ringList = roundStream.filter(it -> it instanceof Ring).map(it -> (Ring) it).collect(Collectors.toList());
        assertEquals(2, ringList.size());
    }
}