package org.casting.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class PlayGameTest {

    private PlayGame playGame;
    private List<Character> characters;

    @BeforeEach
    public void setup() {
        playGame = new PlayGame();
        characters = playGame.buildCharacters();
    }

    @Test
    @DisplayName("Should demonstrate casting via class cast")
    public void shouldPlayGameViaClassCasting() {
        String command = "Advance to the next checkpoint!";
        playGame.playViaClassCast(characters, command);
    }

    @Test
    @DisplayName("Should demonstrate casting via cast operator")
    public void shouldPlayGameViaCastOperator() {
        String command = "Retreat to a safe distance and regroup!";
        playGame.playViaCastOperator(characters, command);
    }

}