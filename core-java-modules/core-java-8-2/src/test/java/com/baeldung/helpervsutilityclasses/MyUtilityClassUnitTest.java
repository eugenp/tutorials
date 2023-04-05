package com.baeldung.helpervsutilityclasses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MyUtilityClassUnitTest {

    @Test
    void whenUsingUtilityMethods_thenAccessMethodsViaClassName(){
        assertEquals( "INIUBONG", MyUtilityClass.returnUpperCase("iniubong"));
        assertEquals("accra", MyUtilityClass.returnLowerCase("AcCrA"));
    }

}