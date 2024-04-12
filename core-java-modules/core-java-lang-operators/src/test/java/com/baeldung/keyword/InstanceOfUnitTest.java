package com.baeldung.keyword;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class InstanceOfUnitTest {

    @Test
    void givenWhenInstanceIsCorrect_thenReturnTrue() {
        Ring ring = new Ring();
        assertTrue(ring instanceof Round);
    }

    @Test
    void givenWhenObjectIsInstanceOfType_thenReturnTrue() {
        Circle circle = new Circle();
        assertTrue(circle instanceof Circle);
    }


    @Test
    void givenWhenInstanceIsOfSubtype_thenReturnTrue() {
        Circle circle = new Circle();
        assertTrue(circle instanceof Round);
    }

    @Test
    void givenWhenTypeIsInterface_thenReturnTrue() {
        Circle circle = new Circle();
        assertTrue(circle instanceof Shape);
    }

    @Test
    void givenWhenTypeIsOfObjectType_thenReturnTrue() {
        Thread thread = new Thread();
        assertTrue(thread instanceof Object);
    }

    @Test
    void givenWhenInstanceValueIsNull_thenReturnFalse() {
        Circle circle = null;
        assertFalse(circle instanceof Round);
    }

    @Test
    void givenWhenComparingClassInDiffHierarchy_thenCompilationError() {
        //assertFalse( circle instanceof Triangle);
    }

    @Test
    void givenWhenStream_whenCastWithoutInstanceOfChk_thenGetException() {
        Stream<Round> roundStream = Stream.of(new Ring(), new Ring(), new Circle());
        assertThrows(ClassCastException.class, () -> roundStream.map(it -> (Ring) it).collect(Collectors.toList()));
    }

    @Test
    void givenWhenStream_whenCastAfterInstanceOfChk_thenGetExpectedResult() {
        Stream<Round> roundStream = Stream.of(new Ring(), new Ring(), new Circle());
        List<Ring> ringList = roundStream.filter(it -> it instanceof Ring).map(it -> (Ring) it).collect(Collectors.toList());
        assertEquals(2, ringList.size());
    }
}