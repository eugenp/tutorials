package com.baeldung.doublecolon;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.doublecolon.function.ComputerPredicate;

public class ComputerUtils {

    static final ComputerPredicate after2010Predicate = (c) -> (c.getAge() > 2010);
    static final ComputerPredicate blackPredicate = (c) -> "black".equals(c.getColor());

    public static List<Computer> filter(final List<Computer> inventory, final ComputerPredicate p) {

        final List<Computer> result = new ArrayList<>();
        inventory.stream().filter(p::filter).forEach(result::add);

        return result;
    }

    static void repair(final Computer computer) {
        if (computer.getHealty() < 50) {
            computer.setHealty(100);
        }
    }

}
