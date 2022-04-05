package com.baeldung.algorithms.play2048;

import java.security.SecureRandom;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Computer {
    private static final Logger LOG = LoggerFactory.getLogger(Computer.class);

    private final SecureRandom rng = new SecureRandom();

    public Board makeMove(Board input) {
        List<Cell> emptyCells = input.emptyCells();
        LOG.info("Number of empty cells: {}", emptyCells.size());

        double numberToPlace = rng.nextDouble();
        LOG.info("New number probability: {}", numberToPlace);

        int indexToPlace = rng.nextInt(emptyCells.size());
        Cell cellToPlace = emptyCells.get(indexToPlace);
        LOG.info("Placing number into empty cell: {}", cellToPlace);

        return input.placeTile(cellToPlace, numberToPlace >= 0.9 ? 4 : 2);
    }
}
