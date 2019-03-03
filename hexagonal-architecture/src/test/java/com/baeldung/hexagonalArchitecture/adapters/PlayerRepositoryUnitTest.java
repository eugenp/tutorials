package com.baeldung.hexagonalArchitecture.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.baeldung.hexagonalArchitecture.adapters.PlayerRepositoryInMemoryImpl;
import com.baeldung.hexagonalArchitecture.adapters.jaxb.PlayerRepositoryJaxbImpl;
import com.baeldung.hexagonalArchitecture.domain.Player;
import com.baeldung.hexagonalArchitecture.ports.PlayerRepository;

public class PlayerRepositoryUnitTest {

    private static PlayerRepositoryJaxbImpl playerJaxbRepo;

    private static PlayerRepositoryInMemoryImpl playerInMemoryRepo;

    @BeforeAll
    public static void setUp() {
        playerJaxbRepo = new PlayerRepositoryJaxbImpl();
        playerInMemoryRepo = new PlayerRepositoryInMemoryImpl();
    }

    @Test
    public void jaxb_impl_whenPlayerSaved_thenSearchedPlayerFound() {
        whenPlayerSaved_thenSearchedPlayerFound(playerJaxbRepo);
    }

    @Test
    public void inMemory_impl_whenPlayerSaved_thenSearchedPlayerFound() {
        whenPlayerSaved_thenSearchedPlayerFound(playerInMemoryRepo);
    }

    public void whenPlayerSaved_thenSearchedPlayerFound(PlayerRepository playerRepo) {
        Player playerJohn = new Player("John", 100);
        playerRepo.savePlayer(playerJohn);

        Player searchedPlayer = playerRepo.getPlayer("John");
        assertEquals("John", searchedPlayer.getName());
        assertEquals(100, searchedPlayer.getScore());
    }
}
