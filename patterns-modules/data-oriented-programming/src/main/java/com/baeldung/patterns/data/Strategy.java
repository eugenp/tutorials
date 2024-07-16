package com.baeldung.patterns.data;

import static com.baeldung.patterns.data.Strategies.*;

public sealed interface Strategy permits Ones, Twos, OnePair, TwoPairs, ThreeOfaKind {
}
