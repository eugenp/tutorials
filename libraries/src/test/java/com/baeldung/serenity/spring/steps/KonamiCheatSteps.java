package com.baeldung.serenity.spring.steps;

import net.thucydides.core.annotations.Step;

import static org.junit.Assert.assertEquals;

/**
 * @author aiet
 */
public class KonamiCheatSteps {

    @Step("all stages of the game are cleared")
    public void gameStageCleared() {
    }

    @Step("input the classic 'Konami Code': {0} ")
    public void cheatWith(String cheatcode) {
        assertEquals("cheatcode wrong", "↑↑↓↓←→←→BA", cheatcode);
    }

    @Step("there is still a stage left")
    public void aStageRemains() {
    }

}
