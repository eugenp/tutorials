package com.baeldung.doublecolumn;

import com.baeldung.doublecolumn.function.ComputerPredicate;

import java.util.ArrayList;
import java.util.List;

public class ComputerUtils {

    public static final ComputerPredicate after2010Predicate = (c) -> (c.getAge() > 2010);
    public static final ComputerPredicate blackPredicate = (c) -> "black".equals(c.getColor());

    public static List<Computer> filter(List<Computer> inventory, ComputerPredicate p) {

        List<Computer> result = new ArrayList<>();
        inventory.stream().filter(p::filter).forEach(result::add);

        return result;
    }

    public static void repair(Computer computer) {
        if (computer.getHealty() < 50) {
            computer.setHealty(100);
        }
    }

}
