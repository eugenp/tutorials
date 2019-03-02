package com.baeldung.hexagonalArchitecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.baeldung.hexagonalArchitecture.adapters.PlayerRepositoryDBImpl;
import com.baeldung.hexagonalArchitecture.adapters.SnakeGameConsole;
import com.baeldung.hexagonalArchitecture.domain.Player;

public class SnakeGameConsoleDBRepoUnitTest {
    private static SnakeGameConsole game;

    private static PlayerRepositoryDBImpl playerRepo;

    @BeforeAll
    public static void setUp() {
        game = new SnakeGameConsole();
        playerRepo = Mockito.mock(PlayerRepositoryDBImpl.class);
    }

    @Test
    public void givenSavedPlayer_whenSearchingPlayer_thenFound() {
        // Given
        game.play();
        Player playerJohn = new Player("John", 100);
        playerRepo.savePlayer(playerJohn);
        game.exit();

        // When
        when(playerRepo.getPlayer("John")).thenReturn(playerJohn);

        // Then
        assertEquals("John", playerJohn.getName());
    }
}
