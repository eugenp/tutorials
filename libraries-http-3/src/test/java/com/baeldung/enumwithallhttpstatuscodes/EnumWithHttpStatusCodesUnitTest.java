package com.baeldung.enumwithallhttpstatuscodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumWithHttpStatusCodesUnitTest {
    
    @Test
    public void givenStatusCode_whenUsingBasicApproach_thenGetCorrectCode() {
        assertEquals(100, HttpStatus.CONTINUE.getCode());
        assertEquals(200, HttpStatus.OK.getCode());
        assertEquals(300, HttpStatus.MULTIPLE_CHOICES.getCode());
        assertEquals(400, HttpStatus.BAD_REQUEST.getCode());
        assertEquals(500, HttpStatus.INTERNAL_SERVER_ERROR.getCode());
    }

    @Test
    public void givenStatusCode_whenUsingBasicApproach_thenGetCorrectDescription() {
        assertEquals("Continue", HttpStatus.CONTINUE.getDescription());
        assertEquals("OK", HttpStatus.OK.getDescription());
        assertEquals("Multiple Choices", HttpStatus.MULTIPLE_CHOICES.getDescription());
        assertEquals("Bad Request", HttpStatus.BAD_REQUEST.getDescription());
        assertEquals("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.getDescription());
    }
}
