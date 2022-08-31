package com.baeldung.math.quadraticequationroot;

import java.util.ArrayList;
import java.util.List;

public class RealRootsCalculator {

    public static List<Double> getPolynomRoots(Polynom polynom) {
        List<Double> roots = new ArrayList<>();
        double discriminant = polynom.getDiscriminant();
        if (discriminant > 0) {
            roots.add((-polynom.getB() - Math.sqrt(discriminant)) / (2 * polynom.getA()));
            roots.add((-polynom.getB() + Math.sqrt(discriminant)) / (2 * polynom.getA()));
        } else if (discriminant == 0) {
            roots.add(-polynom.getB() / (2 * polynom.getA()));
        }
        return roots;
    }

}
