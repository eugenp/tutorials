package com.baeldung.jool;

import org.jooq.lambda.Seq;
import org.jooq.lambda.Unchecked;
import org.jooq.lambda.function.Function1;
import org.jooq.lambda.function.Function2;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.jooq.lambda.tuple.Tuple.tuple;

public class JOOLUnitTest {
    @Test
    public void givenSeq_whenCheckContains_shouldReturnTrue() {
        List<Integer> concat = Seq.of(1, 2, 3).concat(Seq.of(4, 5, 6)).toList();

        assertEquals(concat, Arrays.asList(1, 2, 3, 4, 5, 6));

        assertTrue(Seq.of(1, 2, 3, 4).contains(2));

        assertTrue(Seq.of(1, 2, 3, 4).containsAll(2, 3));

        assertTrue(Seq.of(1, 2, 3, 4).containsAny(2, 5));
    }

    @Test
    public void givenStreams_whenJoin_shouldHaveElementsFromTwoStreams() {
        // given
        Stream<Integer> left = Stream.of(1, 2, 4);
        Stream<Integer> right = Stream.of(1, 2, 3);

        // when
        List<Integer> rightCollected = right.collect(Collectors.toList());
        List<Integer> collect = left.filter(rightCollected::contains).collect(Collectors.toList());

        // then
        assertEquals(collect, Arrays.asList(1, 2));
    }

    @Test
    public void givenSeq_whenJoin_shouldHaveElementsFromBothSeq() {
        assertEquals(Seq.of(1, 2, 4).innerJoin(Seq.of(1, 2, 3), Objects::equals).toList(), Arrays.asList(tuple(1, 1), tuple(2, 2)));

        assertEquals(Seq.of(1, 2, 4).leftOuterJoin(Seq.of(1, 2, 3), Objects::equals).toList(), Arrays.asList(tuple(1, 1), tuple(2, 2), tuple(4, null)));

        assertEquals(Seq.of(1, 2, 4).rightOuterJoin(Seq.of(1, 2, 3), Objects::equals).toList(), Arrays.asList(tuple(1, 1), tuple(2, 2), tuple(null, 3)));

        assertEquals(Seq.of(1, 2).crossJoin(Seq.of("A", "B")).toList(), Arrays.asList(tuple(1, "A"), tuple(1, "B"), tuple(2, "A"), tuple(2, "B")));
    }

    @Test
    public void givenSeq_whenManipulateSeq_seqShouldHaveNewElementsInIt() {
        assertEquals(Seq.of(1, 2, 3).cycle().limit(9).toList(), Arrays.asList(1, 2, 3, 1, 2, 3, 1, 2, 3));

        assertEquals(Seq.of(1, 2, 3).duplicate().map((first, second) -> tuple(first.toList(), second.toList())), tuple(Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3)));

        assertEquals(Seq.of(1, 2, 3, 4).intersperse(0).toList(), Arrays.asList(1, 0, 2, 0, 3, 0, 4));

        assertEquals(Seq.of(1, 2, 3, 4, 5).shuffle().toList().size(), 5);

        assertEquals(Seq.of(1, 2, 3, 4).partition(i -> i > 2).map((first, second) -> tuple(first.toList(), second.toList())), tuple(Arrays.asList(3, 4), Arrays.asList(1, 2))

        );

        assertEquals(Seq.of(1, 2, 3, 4).reverse().toList(), Arrays.asList(4, 3, 2, 1));
    }

    @Test
    public void givenSeq_whenGroupByAndFold_shouldReturnProperSeq() {

        Map<Integer, List<Integer>> expectedAfterGroupBy = new HashMap<>();
        expectedAfterGroupBy.put(1, Arrays.asList(1, 3));
        expectedAfterGroupBy.put(0, Arrays.asList(2, 4));

        assertEquals(Seq.of(1, 2, 3, 4).groupBy(i -> i % 2), expectedAfterGroupBy);

        assertEquals(Seq.of("a", "b", "c").foldLeft("!", (u, t) -> u + t), "!abc");

        assertEquals(Seq.of("a", "b", "c").foldRight("!", (t, u) -> t + u), "abc!");
    }

    @Test
    public void givenSeq_whenUsingSeqWhile_shouldBehaveAsWhileLoop() {

        assertEquals(Seq.of(1, 2, 3, 4, 5).skipWhile(i -> i < 3).toList(), Arrays.asList(3, 4, 5));

        assertEquals(Seq.of(1, 2, 3, 4, 5).skipUntil(i -> i == 3).toList(), Arrays.asList(3, 4, 5));
    }

    @Test
    public void givenSeq_whenZip_shouldHaveZippedSeq() {

        assertEquals(Seq.of(1, 2, 3).zip(Seq.of("a", "b", "c")).toList(), Arrays.asList(tuple(1, "a"), tuple(2, "b"), tuple(3, "c")));

        assertEquals(Seq.of(1, 2, 3).zip(Seq.of("a", "b", "c"), (x, y) -> x + ":" + y).toList(), Arrays.asList("1:a", "2:b", "3:c"));

        assertEquals(Seq.of("a", "b", "c").zipWithIndex().toList(), Arrays.asList(tuple("a", 0L), tuple("b", 1L), tuple("c", 2L)));
    }

    public Integer methodThatThrowsChecked(String arg) throws Exception {
        return arg.length();
    }

    @Test
    public void givenOperationThatThrowsCheckedException_whenExecuteAndNeedToWrapCheckedIntoUnchecked_shouldPass() {
        // when
        List<Integer> collect = Stream.of("a", "b", "c").map(elem -> {
            try {
                return methodThatThrowsChecked(elem);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        // then
        assertEquals(collect, Arrays.asList(1, 1, 1));
    }

    @Test
    public void givenOperationThatThrowsCheckedException_whenExecuteUsingUncheckedFuction_shouldPass() {
        // when
        List<Integer> collect = Stream.of("a", "b", "c").map(Unchecked.function(this::methodThatThrowsChecked)).collect(Collectors.toList());

        // then
        assertEquals(collect, Arrays.asList(1, 1, 1));
    }

    @Test
    public void givenFunction_whenAppliedPartially_shouldAddNumberToPartialArgument() {
        // given
        Function2<Integer, Integer, Integer> addTwoNumbers = (v1, v2) -> v1 + v2;
        addTwoNumbers.toBiFunction();
        Function1<Integer, Integer> addToTwo = addTwoNumbers.applyPartially(2);

        // when
        Integer result = addToTwo.apply(5);

        // then
        assertEquals(result, (Integer) 7);
    }

    @Test
    public void givenSeqOfTuples_whenTransformToLowerNumberOfTuples_shouldHaveProperResult() {
        // given
        Seq<Tuple3<String, String, Integer>> personDetails = Seq.of(tuple("michael", "similar", 49), tuple("jodie", "variable", 43));
        Tuple2<String, String> tuple = tuple("winter", "summer");

        // when
        List<Tuple4<String, String, String, String>> result = personDetails.map(t -> t.limit2().concat(tuple)).toList();

        // then
        assertEquals(result, Arrays.asList(tuple("michael", "similar", "winter", "summer"), tuple("jodie", "variable", "winter", "summer")));
    }
}
