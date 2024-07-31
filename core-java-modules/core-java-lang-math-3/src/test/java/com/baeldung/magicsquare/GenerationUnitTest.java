package com.baeldung.magicsquare;

import org.junit.jupiter.api.Test;

public class GenerationUnitTest {
    @Test
    public void testGenerate3x3() {
        MagicSquare magicSquare = new MagicSquare(3);
        System.out.println(magicSquare);

        magicSquare.validate();
    }

    @Test
    public void testGenerate9x9() {
        MagicSquare magicSquare = new MagicSquare(9);
        System.out.println(magicSquare);

        magicSquare.validate();
    }

    @Test
    public void testGenerate12x12() {
        MagicSquare magicSquare = new MagicSquare(12);
        System.out.println(magicSquare);

        magicSquare.validate();
    }

    @Test
    public void testGenerate10x10() {
        MagicSquare magicSquare = new MagicSquare(10);
        System.out.println(magicSquare);

        magicSquare.validate();
    }

    @Test
    public void testGenerate18x18() {
        MagicSquare magicSquare = new MagicSquare(18);
        System.out.println(magicSquare);

        magicSquare.validate();
    }
}
