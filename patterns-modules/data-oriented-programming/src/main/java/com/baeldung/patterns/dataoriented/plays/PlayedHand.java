package com.baeldung.patterns.dataoriented.plays;

public sealed interface PlayedHand permits MoreOfTheSameKind, Pairs, SpecificDiceValue {
}
