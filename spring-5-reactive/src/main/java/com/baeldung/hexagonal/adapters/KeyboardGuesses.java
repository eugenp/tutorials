package com.baeldung.hexagonal.adapters;

import java.util.Scanner;

import com.baeldung.hexagonal.domain.Guesses;

public class KeyboardGuesses implements Guesses{

	@Override
	public int latestGuess() {
		Scanner keyboard = new Scanner(System.in);
        return keyboard.nextInt();
	}

}
