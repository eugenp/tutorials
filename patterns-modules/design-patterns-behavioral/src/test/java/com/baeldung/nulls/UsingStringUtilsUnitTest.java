package com.baeldung.nulls;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UsingStringUtilsUnitTest {

    private UsingStringUtils classUnderTest;

    @BeforeEach
    public void setup() {
        classUnderTest = new UsingStringUtils();
    }

    @Test
    public void givenArgIsNull_whenCallingAccept_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> classUnderTest.accept(null));
    }

    @Test
    public void givenArgIsEmpty_whenCallingAccept_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> classUnderTest.accept(""));
    }

    @Test
    public void givenArgIsNull_whenCallingAcceptOnlyNonBlank_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> classUnderTest.acceptOnlyNonBlank(null));
    }

    @Test
    public void givenArgIsEmpty_whenCallingAcceptOnlyNonBlank_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> classUnderTest.acceptOnlyNonBlank(""));
    }

    @Test
    public void givenArgIsBlank_whenCallingAcceptOnlyNonBlank_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> classUnderTest.acceptOnlyNonBlank(" "));
    }

}