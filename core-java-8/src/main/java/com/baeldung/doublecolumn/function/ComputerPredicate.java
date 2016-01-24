package com.baeldung.doublecolumn.function;

import com.baeldung.doublecolumn.Computer;


@FunctionalInterface
public interface ComputerPredicate {

    boolean filter(Computer c);

}
