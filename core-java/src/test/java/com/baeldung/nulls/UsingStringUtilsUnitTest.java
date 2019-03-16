package com.baeldung.nulls;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsingStringUtilsUnitTest {

    private UsingStringUtils classUnderTest;

    @BeforeEach
    public void setup() {
        classUnderTest = new UsingStringUtils();
    }

    @Test
    public void givenAccept_whenArgIsNull_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> classUnderTest.accept(null));
    }

    @Test
    public void givenAccept_whenArgIsEmpty_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> classUnderTest.accept(""));
    }

    @Test
    public void givenAcceptOnlyNonBlank_whenArgIsNull_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> classUnderTest.acceptOnlyNonBlank(null));
    }

    @Test
    public void givenAcceptOnlyNonBlank_whenArgIsEmpty_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> classUnderTest.acceptOnlyNonBlank(""));
    }

    @Test
    public void givenAcceptOnlyNonBlank_whenArgIsBlank_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> classUnderTest.acceptOnlyNonBlank(" "));
    }

}