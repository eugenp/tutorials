package com.baeldung.patterns.dataoriented.plays;

import java.util.List;

public record MoreOfTheSameKind(List<Integer> dices, int nrOfDicesOfSameKind) implements PlayedHand {
}
