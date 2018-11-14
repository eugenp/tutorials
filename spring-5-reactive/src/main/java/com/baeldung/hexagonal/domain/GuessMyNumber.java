package com.baeldung.hexagonal.domain;

public class GuessMyNumber {
    private final int numberToGuess;
    private Guesses guesses;
    private Display display;

    public GuessMyNumber(int numberToGuess, final Guesses guesses, final Display display) {
        this.numberToGuess = numberToGuess;
        this.guesses = guesses;
        this.display = display;
    }

    public void play() {
        displayIntroduction();
        boolean guessedCorrectly = givePlayerUpToFiveAttemptsToGuess();
        displayFinalResult(guessedCorrectly);
    }

    private boolean givePlayerUpToFiveAttemptsToGuess() {
        int attemptNumber = 1;
        boolean guessedCorrectly = false;

        while ( attemptNumber <= 5 && !guessedCorrectly){
            displayPrompt(attemptNumber);

            if ( guesses.latestGuess() == numberToGuess ){
                guessedCorrectly = true ;
            }
            else {
                display.show("Try again");
            }

            attemptNumber++;
        }

        return guessedCorrectly;
    }

    private void displayPrompt(int attemptNumber) {
        display.show(String.format("Your Guess? (attempt %d) >", attemptNumber));
    }

    private void displayIntroduction() {
        display.show("I'm thinking of a number between 1 and 20.");
        display.show("Can you guess it, in five guesses?");
    }

    private void displayFinalResult(boolean guessedCorrectly) {
        if ( guessedCorrectly ) {
            display.show("You guessed it right! ");
        }
        else {
            display.show("Bad luck. Play Again !!");
        }
    }

}
