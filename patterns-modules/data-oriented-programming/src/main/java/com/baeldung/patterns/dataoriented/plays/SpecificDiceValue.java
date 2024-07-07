package com.baeldung.patterns.dataoriented.plays;

import java.util.List;

public record SpecificDiceValue(List<Integer> dices, int diceValue) implements PlayedHand {
}
