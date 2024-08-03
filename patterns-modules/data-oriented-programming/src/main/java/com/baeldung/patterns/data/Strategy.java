package com.baeldung.patterns.data;

import static com.baeldung.patterns.data.Strategies.OnePair;
import static com.baeldung.patterns.data.Strategies.Ones;
import static com.baeldung.patterns.data.Strategies.ThreeOfaKind;
import static com.baeldung.patterns.data.Strategies.TwoPairs;
import static com.baeldung.patterns.data.Strategies.Twos;

public sealed interface Strategy permits Ones, Twos, OnePair, TwoPairs, ThreeOfaKind {

}
