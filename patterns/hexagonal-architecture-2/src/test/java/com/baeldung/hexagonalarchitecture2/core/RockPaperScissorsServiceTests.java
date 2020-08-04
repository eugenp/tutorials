package com.baeldung.hexagonalarchitecture2.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.baeldung.hexagonalarchitecture2.adapters.InMemoryGameRepositoryAdapter;
import com.baeldung.hexagonalarchitecture2.core.domain.Game;
import com.baeldung.hexagonalarchitecture2.core.domain.GameInfo;
import com.baeldung.hexagonalarchitecture2.core.domain.Move;
import com.baeldung.hexagonalarchitecture2.core.domain.Result;
import com.baeldung.hexagonalarchitecture2.core.ports.GameRepository;

public class RockPaperScissorsServiceTests {

    private final InMemoryGameRepositoryAdapter gameRepository = new InMemoryGameRepositoryAdapter();
    private final RockPaperScissorsService rpsService = new RockPaperScissorsService(gameRepository);

    @Test
    public void startNewGame_returnGameWithIdAndHMAC() throws Exception {
        Game game = rpsService.startNewGame();
        assertNotNull(game.getMoveHMAC());
        // Check new game has been persisted to repository
        GameInfo gameInfo = gameRepository.getAndRemoveGame(game.getId());
        assertNotNull(gameInfo);
        assertEquals(game.getId(), gameInfo.getId());
    }

    @Test
    public void playGame_GameDoesNotExist_throwException() {
        assertThrows(GameRepository.GameNotFoundException.class, () -> rpsService.playGame(1234567890, Move.ROCK));
    }

    @Test
    public void playGame_GameExists_returnValidResultAndValidHMAC() throws Exception {
        Game game = rpsService.startNewGame();
        Move playerMove = Move.ROCK;
        Result result = rpsService.playGame(game.getId(), playerMove);
        assertNotNull(result);
        assertEquals(playerMove, result.getPlayerMove());
        String hmac = HmacGenerator.calculateHMAC(String.valueOf(result.getComputerMove()), result.getKey());
        assertEquals(game.getMoveHMAC(), hmac);
    }

    @Test
    public void getOutcome_verifyRPSLogic() {
        // Ties
        assertEquals(Result.Outcome.TIE, rpsService.getOutcome(Move.ROCK, Move.ROCK));
        assertEquals(Result.Outcome.TIE, rpsService.getOutcome(Move.PAPER, Move.PAPER));
        assertEquals(Result.Outcome.TIE, rpsService.getOutcome(Move.SCISSORS, Move.SCISSORS));

        // Wins
        assertEquals(Result.Outcome.WIN, rpsService.getOutcome(Move.ROCK, Move.SCISSORS));
        assertEquals(Result.Outcome.WIN, rpsService.getOutcome(Move.PAPER, Move.ROCK));
        assertEquals(Result.Outcome.WIN, rpsService.getOutcome(Move.SCISSORS, Move.PAPER));

        // Losses
        assertEquals(Result.Outcome.LOSS, rpsService.getOutcome(Move.SCISSORS, Move.ROCK));
        assertEquals(Result.Outcome.LOSS, rpsService.getOutcome(Move.ROCK, Move.PAPER));
        assertEquals(Result.Outcome.LOSS, rpsService.getOutcome(Move.PAPER, Move.SCISSORS));
    }
}
