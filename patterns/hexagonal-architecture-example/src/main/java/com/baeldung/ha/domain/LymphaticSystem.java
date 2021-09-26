package com.baeldung.ha.domain;

import java.util.Random;

import static java.util.Objects.isNull;

public class LymphaticSystem {
    public static int MAX_ANTIGEN_VALUE = 100;
    private static Random RANDOM = new Random();

    public static Antibody produce(Antigen antigen) throws InvalidAntigenException {
        // Validate the antigen
        validate(antigen);

        // Work for an antibody
        int value;
        int effort = 0;
        do {
            value = RANDOM.nextInt(MAX_ANTIGEN_VALUE);
            effort++;
        } while (antigen.getValue() != value);

        return new Antibody(antigen, effort);
    }

    private static void validate(Antigen antigen) throws InvalidAntigenException {
        if (isNull(antigen)) {
            throw new InvalidAntigenException(null);
        }

        if (antigen.getValue() < 0 || antigen.getValue() >= MAX_ANTIGEN_VALUE) {
            throw new InvalidAntigenException(antigen.getValue());
        }
    }
}
