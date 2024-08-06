package com.baeldung.casting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class PlayGameUnitTest {

    private PlayGame playGame;
    private Commander commander;
    private Warrior warrior;
    private List<Character> characters;

    @BeforeEach
    void setUp() {
        playGame = new PlayGame();
        commander = Mockito.mock(Commander.class);
        warrior = Mockito.mock(Warrior.class);
        characters = new ArrayList<>();
        characters.add(commander);
        characters.add(warrior);
    }

    @Test
    public void whenCastingViaClass_thenSuccess()  {
        String command = "Attack";

        List<Character> spyCharacters = new ArrayList<>();
        spyCharacters.add(spy(new Commander("Odin")));
        spyCharacters.add(spy(new Warrior("Thor")));

        playGame.playViaClassCast(spyCharacters, command);

        verify((Warrior) spyCharacters.get(1), times(1)).obeyCommand(command);
        verify((Commander) spyCharacters.get(0), times(1)).issueCommand(command);
    }

    @Test
    public void whenCastingViaOperator_thenSuccess() throws NoSuchMethodException {
        String command = "Retreat to a safe distance and regroup!";

        List<Character> spyCharacters = new ArrayList<>();
        spyCharacters.add(spy(new Commander("Odin")));
        spyCharacters.add(spy(new Warrior("Thor")));

        playGame.playViaCastOperator(spyCharacters, command);

        verify((Warrior) spyCharacters.get(1), times(1)).obeyCommand(command);
        verify((Commander) spyCharacters.get(0), times(1)).issueCommand(command);
    }

}
