package com.baeldung.hexagonalarchitecture2.core.domain;

public class Result {
    private Outcome outcome;
    private Move playerMove;
    private Move computerMove;
    private String key;
    private String message;

    public Result(Outcome outcome, Move playerMove, Move computerMove, String key) {
        this.outcome = outcome;
        this.playerMove = playerMove;
        this.computerMove = computerMove;
        this.key = key;

        switch (outcome) {
        case WIN:
            this.message = "Well played! Double or nothing?";
            break;
        case LOSS:
            this.message = "I win! Check the HMAC if you don't believe me.";
            break;
        case TIE:
            this.message = "Great minds think alike!";
            break;
        }
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public Move getPlayerMove() {
        return playerMove;
    }

    public void setPlayerMove(Move playerMove) {
        this.playerMove = playerMove;
    }

    public Move getComputerMove() {
        return computerMove;
    }

    public void setComputerMove(Move computerMove) {
        this.computerMove = computerMove;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum Outcome {
        WIN, LOSS, TIE
    }
}
