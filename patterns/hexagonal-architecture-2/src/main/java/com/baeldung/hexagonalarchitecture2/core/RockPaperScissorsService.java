package com.baeldung.hexagonalarchitecture2.core;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import com.baeldung.hexagonalarchitecture2.core.domain.Game;
import com.baeldung.hexagonalarchitecture2.core.domain.GameInfo;
import com.baeldung.hexagonalarchitecture2.core.domain.Move;
import com.baeldung.hexagonalarchitecture2.core.domain.Result;
import com.baeldung.hexagonalarchitecture2.core.domain.Result.Outcome;
import com.baeldung.hexagonalarchitecture2.core.ports.GameRepository;
import com.baeldung.hexagonalarchitecture2.core.ports.GameRepository.GameNotFoundException;

public class RockPaperScissorsService {

    private final GameRepository gameRepository;

    public RockPaperScissorsService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game startNewGame() throws Exception {
        Move move = Move.convertToMove(new Random().nextInt(3));
        String key = RandomStringUtils.randomAlphanumeric(12);
        String hmac = HmacGenerator.calculateHMAC(move.toString(), key);
        int id = gameRepository.saveNewGame(move, key);
        return new Game(id, hmac);
    }

    public Result playGame(int id, Move playerMove) throws GameNotFoundException {
        GameInfo gameInfo = gameRepository.getAndRemoveGame(id);
        Move computerMove = gameInfo.getMove();
        Outcome outcome = getOutcome(playerMove, computerMove);
        return new Result(outcome, playerMove, computerMove, gameInfo.getKey());

    }

    Outcome getOutcome(Move playerMove, Move computerMove) {
        if (playerMove == computerMove) {
            return Outcome.TIE;
        }

        if (playerMove == Move.ROCK) {
            if (computerMove == Move.PAPER) {
                return Outcome.LOSS;
            } else {
                return Outcome.WIN;
            }
        } else if (playerMove == Move.PAPER) {
            if (computerMove == Move.SCISSORS) {
                return Outcome.LOSS;
            } else {
                return Outcome.WIN;
            }
        } else { // SCISSORS
            if (computerMove == Move.ROCK) {
                return Outcome.LOSS;
            } else {
                return Outcome.WIN;
            }
        }
    }
}
