package com.baeldung.helperVsUtilityClasses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MyUtilityClassUnitTest {

    @Test
    void whenUsingUtilityMethods_thenAccessMethodsViaClassName(){
        assertEquals(MyUtilityClass.returnUpperCase("iniubong"), "INIUBONG");
        assertEquals(MyUtilityClass.returnLowerCase("AcCrA"), "accra");
    }

}