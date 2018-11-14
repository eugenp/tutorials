package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapters.ConsoleDisplay;
import com.baeldung.hexagonal.adapters.KeyboardGuesses;
import com.baeldung.hexagonal.domain.Display;
import com.baeldung.hexagonal.domain.Guesses;
import com.baeldung.hexagonal.domain.GuessMyNumber;

public class Game {

	public static void main(String [] args){
		Display display = new ConsoleDisplay();
        Guesses guesses = new KeyboardGuesses();

        new GuessMyNumber(3, guesses, display).play();
	}
}
