package com.baeldung.treeset.collectintotreeset;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class CollectIntoTreeSetUnitTest {
    Player kai = new Player("Kai", 26, 28, 7);
    Player eric = new Player("Eric", 28, 30, 11);
    Player saajan = new Player("Saajan", 30, 100, 66);
    Player kevin = new Player("Kevin", 24, 50, 49);

    @Test
    void givenAStream_whenCollectIntoTreeSetWithNaturalOrder_thenGetExpectedResult() {
        String kotlin = "Kotlin";
        String java = "Java";
        String python = "Python";
        String ruby = "Ruby";
        TreeSet<String> myTreeSet = Stream.of(ruby, java, kotlin, python)
            .collect(Collectors.toCollection(TreeSet::new));
        assertThat(myTreeSet).containsExactly(java, kotlin, python, ruby);
    }

    @Test
    void givenAPlayerStream_whenCollectIntoTreeSet_thenGetExpectedResult() {
        TreeSet<Player> myTreeSet = Stream.of(saajan, eric, kai, kevin)
            .collect(Collectors.toCollection(TreeSet::new));
        assertThat(myTreeSet).containsExactly(kevin, kai, eric, saajan);
    }

    @Test
    void givenAPlayerStream_whenCollectIntoTreeSetWithNoOfWinsComparator_thenGetExpectedResult() {
        TreeSet<Player> myTreeSet = Stream.of(saajan, eric, kai, kevin)
            .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingInt(Player::getNumberOfWins))));
        assertThat(myTreeSet).containsExactly(kai, eric, kevin, saajan);
    }

    @Test
    void givenAPlayerStream_whenCollectIntoTreeSetWithWinRateComparator_thenGetExpectedResult() {
        TreeSet<Player> myTreeSet = Stream.of(saajan, eric, kai, kevin)
            .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(player -> BigDecimal.valueOf(player.getNumberOfWins())
                .divide(BigDecimal.valueOf(player.getNumberOfPlayed()), 2, RoundingMode.HALF_UP)))));
        assertThat(myTreeSet).containsExactly(kai, eric, saajan, kevin);
    }
}
