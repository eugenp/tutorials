package com.baeldung.hexagonalarchitecture2.graphql;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.hexagonalarchitecture2.core.RockPaperScissorsService;
import com.baeldung.hexagonalarchitecture2.core.domain.Game;
import com.baeldung.hexagonalarchitecture2.core.domain.Move;
import com.baeldung.hexagonalarchitecture2.core.domain.Result;
import com.baeldung.hexagonalarchitecture2.core.ports.GameRepository;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;

public class Mutation implements GraphQLMutationResolver {
    private final RockPaperScissorsService rockPaperScissorsService;

    public Mutation(RockPaperScissorsService rockPaperScissorsService) {
        this.rockPaperScissorsService = rockPaperScissorsService;
    }

    public Game startNewGame() {
        try {
            return rockPaperScissorsService.startNewGame();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to start new game");
        }
    }

    public Result playGame(int id, Move move) {
        try {
            return rockPaperScissorsService.playGame(id, move);
        } catch (GameRepository.GameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "game with given ID not found");
        }
    }

}
