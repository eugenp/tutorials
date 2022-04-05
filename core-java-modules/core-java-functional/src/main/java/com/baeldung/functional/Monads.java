package com.baeldung.functional;

import java.util.Optional;

public class Monads {

    public static Optional<Integer> add(Optional<Integer> val1, Optional<Integer> val2) {
        return val1.flatMap(first -> val2.flatMap(second -> Optional.of(first + second)));
    }

}
