package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapters.ConsoleDisplay;
import com.baeldung.hexagonal.adapters.KeyboardGuesses;
import com.baeldung.hexagonal.domain.Display;
import com.baeldung.hexagonal.domain.Guesses;
import com.baeldung.hexagonal.domain.HexagonalGuessMyNumber;

public class Main {

	public static void main(String [] args){
		Display display = new ConsoleDisplay();
        Guesses guesses = new KeyboardGuesses();

        new HexagonalGuessMyNumber(3, guesses, display).play();
	}
}
