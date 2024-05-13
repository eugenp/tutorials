package com.baeldung.optionalallpreset;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

public class OptionalAllPresentUnitTest {

    @Test
    public void givenThreeOptionals_performAction_onlyIfAllPresent_usingIfCondition() {
        var name = Optional.of("Jean-Luc Picard");
        var designation = Optional.of("Captain");
        var ship = Optional.of("USS Enterprise D");
        var action = false;
        if (name.isPresent() && designation.isPresent() && ship.isPresent()) {
            action = true;
        }
        Assertions.assertTrue(action);
    }

    @Test
    public void givenThreeOptionals_performNoAction_ifNotAllPresent_usingIfCondition() {
        var name = Optional.of("Jean-Luc Picard");
        var designation = Optional.of("Captain");
        var ship = Optional.empty();
        var action = false;
        if (name.isPresent() && designation.isPresent() && ship.isPresent()) {
            action = true;
        }
        Assertions.assertFalse(action);
    }

    @Test
    public void givenThreeOptionals_performAction_onlyIfAllPresent_usingFlatMap() {
        var name = Optional.of("Jean-Luc Picard");
        var designation = Optional.of("Captain");
        var ship = Optional.of("USS Enterprise D");
        Optional<Boolean> action = name.flatMap(n -> designation.flatMap(d -> ship.map(s -> true)));

        Assertions.assertEquals(action, Optional.of(true));
    }

    @Test
    public void givenThreeOptionals_performNoAction_ifNotAllPresent_usingFlatMap() {
        var name = Optional.of("Jean-Luc Picard");
        var designation = Optional.of("Captain");
        var ship = Optional.empty();
        var action = name.flatMap(n -> designation.flatMap(d -> ship.map(s -> true)));

        Assertions.assertTrue(action.isEmpty());
    }

    @Test
    public void givenThreeOptionals_performAction_onlyIfAllPresent_usingIfPresent() {
        var name = Optional.of("Jean-Luc Picard");
        var designation = Optional.of("Captain");
        var ship = Optional.of("USS Enterprise D");
        name.ifPresent(n -> designation.ifPresent(d -> ship.ifPresent(s -> System.out.println("Perform action instead!"))));
        // Intentionally not asserting since the ifPresent returns void
    }

    @Test
    public void givenThreeOptionals_performAction_onlyIfAllPresent_usingStream() {
        var name = Optional.of("Jean-Luc Picard");
        var designation = Optional.of("Captain");
        var ship = Optional.of("USS Enterprise D");
        var status = false;
        var allPresent = Stream.of(name, designation, ship).allMatch(Optional::isPresent);
        if (allPresent) {
            // Perform action if al values present
            status = true;
        }
        Assertions.assertTrue(status);
    }

    @Test
    public void givenThreeOptionals_performNoAction_ifNotAllPresent_usingStream() {
        var name = Optional.of("Jean-Luc Picard");
        var designation = Optional.of("Captain");
        var ship = Optional.empty();
        var status = false;
        var allPresent = Stream.of(name, designation, ship).allMatch(Optional::isPresent);
        if (allPresent) {
            // Perform action if all values present
            status = true;
        }
        Assertions.assertFalse(status);
    }

}
