package com.baeldung.magicsquare;

import org.junit.jupiter.api.Test;

public class ValidationUnitTest {
    @Test
    public void testValidate3x3() {
        MagicSquare magicSquare = new MagicSquare(3);
        magicSquare.setCell(0, 0, 8);
        magicSquare.setCell(1, 0, 1);
        magicSquare.setCell(2, 0, 6);
        magicSquare.setCell(0, 1, 3);
        magicSquare.setCell(1, 1, 5);
        magicSquare.setCell(2, 1, 7);
        magicSquare.setCell(0, 2, 4);
        magicSquare.setCell(1, 2, 9);
        magicSquare.setCell(2, 2, 2);

        magicSquare.validate();
    }
}
