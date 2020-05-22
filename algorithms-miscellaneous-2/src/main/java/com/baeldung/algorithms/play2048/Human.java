package com.baeldung.algorithms.play2048;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Human {
    private static final Logger LOG = LoggerFactory.getLogger(Human.class);

    private SecureRandom rng = new SecureRandom();

    public Board makeMove(Board input) {
        Move move = Move.values()[rng.nextInt(4)];
        LOG.debug("Making move: {}", move);

        return input.move(move);
    }

}
