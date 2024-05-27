package com.baeldung.casting;

import org.junit.jupiter.api.BeforeEach;
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
    public void whenCastingViaClass_thenSuccess() {
        String command = "Advance to the next checkpoint!";
        playGame.playViaClassCast(characters, command);
    }

    @Test
    public void whenCastingViaOperator_thenSuccess() {
        String command = "Retreat to a safe distance and regroup!";
        playGame.playViaCastOperator(characters, command);
    }

}