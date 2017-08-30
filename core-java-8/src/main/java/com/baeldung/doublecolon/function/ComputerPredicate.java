package com.baeldung.doublecolon.function;

import com.baeldung.doublecolon.Computer;

@FunctionalInterface
public interface ComputerPredicate {

    boolean filter(Computer c);

}
