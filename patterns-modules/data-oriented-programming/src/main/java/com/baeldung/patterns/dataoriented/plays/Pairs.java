package com.baeldung.patterns.dataoriented.plays;

import java.util.List;

public record Pairs(List<Integer> dices, int nrOfPairs)  implements PlayedHand {
}
