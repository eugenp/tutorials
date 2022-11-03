package com.baeldung.math.quadraticequationroot;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComplexRootsCalculatorUnitTest {

    @Test
    void givenPolynomWithStrictlyPositiveDiscriminant_whenGetPolynomRoots_ThenReturnBothRealRoots() {
        Polynom polynom = new Polynom(1d, 1d, -6d);
        List<Complex> roots = ComplexRootsCalculator.getPolynomRoots(polynom);
        assertEquals(2, roots.size());
        assertTrue(roots.stream().anyMatch(c -> c.getRealPart() == 2d && c.getImaginaryPart() == 0));
        assertTrue(roots.stream().anyMatch(c -> c.getRealPart() == -3d && c.getImaginaryPart() == 0));
    }

    @Test
    void givenPolynomWithDiscriminantEqualsZero_whenGetPolynomRoots_ThenReturnRoot() {
        Polynom polynom = new Polynom(1d, 4d, 4d);
        List<Complex> roots = ComplexRootsCalculator.getPolynomRoots(polynom);
        assertEquals(1, roots.size());
        assertTrue(roots.get(0).getRealPart() == -2d && roots.get(0).getImaginaryPart() == 0d);
    }

    @Test
    void givenPolynomWithStrictlyNegativeDiscriminant_whenGetPolynomRoots_ThenReturnBothComplexRoot() {
        Polynom polynom = new Polynom(1d, -4d, 8d);
        List<Complex> roots = ComplexRootsCalculator.getPolynomRoots(polynom);
        assertEquals(2, roots.size());
        assertTrue(roots.stream().anyMatch(c -> c.getRealPart() == 2d && c.getImaginaryPart() == 2d));
        assertTrue(roots.stream().anyMatch(c -> c.getRealPart() == 2d && c.getImaginaryPart() == -2d));
    }

}
