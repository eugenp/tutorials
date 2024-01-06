package com.baeldung.staticfinal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BikeUnitTest {

    @Test
    void givenTireConstantSetUponDeclaration_whenGetTire_thenReturnTwo() {
        assertEquals(2, Bike.TIRE);
    }

    @Test
    void givenPedalConstantSetByStaticBlock_whenGetPedal_thenReturnFive() {
        assertEquals(5, Bike.PEDAL);
    }

    @Test
    void givenPartConstantObject_whenObjectStateChanged_thenCorrect() {
        Bike.PART.put("seat", 1);
        assertEquals(1, Bike.PART.get("seat"));

        Bike.PART.put("seat", 5);
        assertEquals(5, Bike.PART.get("seat"));
    }

    @Test
    void givenMathClass_whenAccessingPiConstant_thenVerifyPiValueIsCorrect() {
        assertEquals(3.141592653589793, Math.PI);
    }

}