package core;

/*
 * Core Logic
 */
public class GuessTheNumber {

	private IGuesseNumbers guess;
	private IDisplayNumber display;
	private final int numberToGuess;

	//Injecting object to respective class.
	public GuessTheNumber(IGuesseNumbers guess, IDisplayNumber display,
			int numberToGuess) {
		this.numberToGuess = numberToGuess;
		this.display = display;
		this.guess = guess;

	}

	public void playIt() {
		//1. Show Menu
		showMenu();
		//2. Guessing attempts
		boolean guessAttempts = giveFiveAttempts();
		//3. Show final result
		showlResult(guessAttempts);
	}

	//Method to validate attempts
	private boolean giveFiveAttempts() {
		int attemptNumber = 1;
		boolean guessAttempt = false;

		while (!guessAttempt && attemptNumber <= 5) {
			showCommandLine(attemptNumber);

			if (guess.currentGuess() == numberToGuess) {
				guessAttempt = true;
			} else {
				display.showNumber("Sorry!!, Please Try again");
			}

			attemptNumber++;
		}

		return guessAttempt;
	}

	private void showCommandLine(int attemptNumber) {
		display.showNumber(String.format("YOUR GUESS (attempt %d) --->",
				attemptNumber));
	}
	
	//Show menu
	private void showMenu() {
		display.showNumber("PLEASE ENTER THE NUMBER BETWEEN 1 and 10.");
		display.showNumber("PLEASE GUESS IT IN FIVE ATTEMPTS!!!");
	}

	//Show result
	private void showlResult(boolean guessAttempts) {
		if (guessAttempts) {
			display.showNumber("Congratulations!!! You have guessed it correctly.");
		} else {
			display.showNumber("Sorry!!! Please try again.");
		}
	}

}
