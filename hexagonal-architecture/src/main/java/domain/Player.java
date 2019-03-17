package domain;

import port.IMoveDecider;

public class Player {
    private String name;
    private Board.Symbol symbol;
    private IMoveDecider decider;

    public Player(String name, Board.Symbol symbol, IMoveDecider decider) {
        this.name = name;
        this.symbol = symbol;
        this.decider = decider;
    }

    public Board.Symbol getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public int[] decide() {
        return decider.decide();
    }
}
