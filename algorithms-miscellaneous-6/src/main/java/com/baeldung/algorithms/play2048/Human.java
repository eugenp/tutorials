package com.baeldung.algorithms.play2048;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.math3.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Human {
    private static final Logger LOG = LoggerFactory.getLogger(Human.class);

    public Board makeMove(Board input) {
        // For each move in MOVE
        //   Generate board from move
        //   Generate Score for Board
        // Return board with the best score
        //
        // Generate Score
        //   If Depth Limit
        //     Return Final Score
        //   Total Score = 0
        //   For every empty square in new board
        //     Generate board with "2" in square
        //       Calculate Score
        //       Total Score += (Score * 0.9)
        //     Generate board with "4" in square
        //       Calculate Score
        //       Total Score += (Score * 0.1)
        //
        // Calculate Score
        //   For each move in MOVE
        //     Generate board from move
        //     Generate score for board
        //   Return the best generated score

        return Arrays.stream(Move.values())
            .parallel()
            .map(input::move)
            .filter(board -> !board.equals(input))
            .max(Comparator.comparingInt(board -> generateScore(board, 0)))
            .orElse(input);
    }

    private int generateScore(Board board, int depth) {
        if (depth >= 3) {
            int finalScore = calculateFinalScore(board);
            LOG.debug("Final score for board {}: {}", board,finalScore);
            return finalScore;
        }

        return board.emptyCells().stream()
            .parallel()
            .flatMap(cell -> Stream.of(new Pair<>(cell, 2), new Pair<>(cell, 4)))
            .mapToInt(move -> {
                LOG.debug("Simulating move {} at depth {}", move, depth);
                Board newBoard = board.placeTile(move.getFirst(), move.getSecond());
                int boardScore = calculateScore(newBoard, depth + 1);
                int calculatedScore = (int) (boardScore * (move.getSecond() == 2 ? 0.9 : 0.1));
                LOG.debug("Calculated score for board {} and move {} at depth {}: {}", newBoard, move, depth, calculatedScore);
                return calculatedScore;
            })
            .sum();
    }

    private int calculateScore(Board board, int depth) {
        return Arrays.stream(Move.values())
            .parallel()
            .map(board::move)
            .filter(moved -> !moved.equals(board))
            .mapToInt(newBoard -> generateScore(newBoard, depth))
            .max()
            .orElse(0);
    }

    private int calculateFinalScore(Board board) {
        List<List<Integer>> rowsToScore = new ArrayList<>();
        for (int i = 0; i < board.getSize(); ++i) {
            List<Integer> row = new ArrayList<>();
            List<Integer> col = new ArrayList<>();

            for (int j = 0; j < board.getSize(); ++j) {
                row.add(board.getCell(new Cell(i, j)));
                col.add(board.getCell(new Cell(j, i)));
            }

            rowsToScore.add(row);
            rowsToScore.add(col);
        }

        return rowsToScore.stream()
            .parallel()
            .mapToInt(row -> {
                List<Integer> preMerged = row.stream()
                    .filter(value -> value != 0)
                    .collect(Collectors.toList());

                int numMerges = 0;
                int monotonicityLeft = 0;
                int monotonicityRight = 0;
                for (int i = 0; i < preMerged.size() - 1; ++i) {
                    Integer first = preMerged.get(i);
                    Integer second = preMerged.get(i + 1);
                    if (first.equals(second)) {
                        ++numMerges;
                    } else if (first > second) {
                        monotonicityLeft += first - second;
                    } else {
                        monotonicityRight += second - first;
                    }
                }

                int score = 1000;
                score += 250 * row.stream().filter(value -> value == 0).count();
                score += 750 * numMerges;
                score -= 10 * row.stream().mapToInt(value -> value).sum();
                score -= 50 * Math.min(monotonicityLeft, monotonicityRight);
                return score;
            })
            .sum();
    }
}
