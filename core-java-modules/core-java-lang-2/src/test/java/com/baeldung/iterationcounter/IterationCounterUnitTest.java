package com.baeldung.iterationcounter;

import static com.baeldung.iterationcounter.IterationCounter.IMDB_TOP_MOVIES;
import static com.baeldung.iterationcounter.IterationCounter.getRankingsWithForEachLoop;
import static com.baeldung.iterationcounter.IterationCounter.getRankingsWithForLoop;
import static com.baeldung.iterationcounter.IterationCounter.getRankingsWithFunctionalForEachLoop;
import static com.baeldung.iterationcounter.IterationCounter.getRankingsWithStream;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class IterationCounterUnitTest {
    @Test
    public void givenRankings_whenCalculateWithForLoop_thenRankingsCorrect() {
        assertThat(getRankingsWithForLoop(IMDB_TOP_MOVIES))
          .containsExactly("1: The Shawshank Redemption",
            "2: The Godfather", "3: The Godfather II", "4: The Dark Knight");
    }

    @Test
    public void givenRankings_whenCalculateWithForEachLoop_thenRankingsCorrect() {
        assertThat(getRankingsWithForEachLoop(IMDB_TOP_MOVIES))
          .containsExactly("1: The Shawshank Redemption",
            "2: The Godfather", "3: The Godfather II", "4: The Dark Knight");
    }

    @Test
    public void givenRankings_whenCalculateWithFunctionalForEach_thenRankingsCorrect() {
        assertThat(getRankingsWithFunctionalForEachLoop(IMDB_TOP_MOVIES))
          .containsExactly("1: The Shawshank Redemption",
            "2: The Godfather", "3: The Godfather II", "4: The Dark Knight");
    }

    @Test
    public void givenRankings_whenCalculateWithStream_thenRankingsCorrect() {
        assertThat(getRankingsWithStream(IMDB_TOP_MOVIES.stream()))
          .containsExactly("1: The Shawshank Redemption",
            "2: The Godfather", "3: The Godfather II", "4: The Dark Knight");
    }
}
