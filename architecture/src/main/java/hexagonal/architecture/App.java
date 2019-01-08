package hexagonal.architecture;

import adapter.DisplayResult;
import adapter.GuesseNumbers;
import core.GuessTheNumber;
import core.IDisplayNumber;
import core.IGuesseNumbers;

/*
 * Guess Number, a main class showing hexagonal architecture using ports and adapters.
 */
public class App {
	public static void main(String[] commandLineArguments) {

		// Create objects of DisplayResult and GuesseNumbers
		IDisplayNumber objDisplay = new DisplayResult();
		IGuesseNumbers objGuesses = new GuesseNumbers();

		// Passing object that which one to use, Dependency Injection
		new GuessTheNumber(objGuesses, objDisplay, 10).playIt();
	}
}
