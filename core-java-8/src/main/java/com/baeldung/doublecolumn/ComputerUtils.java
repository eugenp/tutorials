package com.baeldung.doublecolumn;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.doublecolumn.function.ComputerPredicate;

public class ComputerUtils {

    public static final ComputerPredicate after2010Predicate = (c) -> (c.getAge() > 2010);
    public static final ComputerPredicate blackPredicate = (c) -> "black".equals(c.getColor());

    public static List<Computer> filter(final List<Computer> inventory, final ComputerPredicate p) {

        final List<Computer> result = new ArrayList<>();
        inventory.stream().filter(p::filter).forEach(result::add);

        return result;
    }

    public static void repair(final Computer computer) {
        if (computer.getHealty() < 50) {
            computer.setHealty(100);
        }
    }

}
