package com.baeldung.math.quadraticequationroot;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RealRootsCalculatorUnitTest {

    @Test
    void givenPolynomWithStrictlyPositiveDiscriminant_whenGetPolynomRoots_ThenReturnBothRoots() {
        Polynom polynom = new Polynom(1d, 1d, -6d);
        List<Double> roots = RealRootsCalculator.getPolynomRoots(polynom);
        assertEquals(2, roots.size());
        assertTrue(roots.containsAll(Arrays.asList(2d, -3d)));
    }

    @Test
    void givenPolynomWithDiscriminantEqualsZero_whenGetPolynomRoots_ThenReturnRoot() {
        Polynom polynom = new Polynom(1d, 4d, 4d);
        List<Double> roots = RealRootsCalculator.getPolynomRoots(polynom);
        assertEquals(1, roots.size());
        assertTrue(roots.get(0).equals(-2d));
    }

    @Test
    void givenPolynomWithStrictlyNegativeDiscriminant_whenGetPolynomRoots_ThenReturnNoRoot() {
        Polynom polynom = new Polynom(3d, 2d, 5d);
        List<Double> roots = RealRootsCalculator.getPolynomRoots(polynom);
        assertEquals(0, roots.size());
    }

}
