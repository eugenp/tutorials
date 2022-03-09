package org.baeldung.eval.hexagonalarchitecture.excpetion;

import java.util.UUID;

public class PoemNotFoundException extends RuntimeException {

    public PoemNotFoundException(UUID id) {
        super("Pome #id %s not found!".formatted(id));
    }
}

