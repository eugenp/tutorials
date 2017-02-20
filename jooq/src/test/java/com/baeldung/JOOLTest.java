package com.baeldung;


import org.jooq.lambda.Seq;
import org.jooq.lambda.Unchecked;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.jooq.lambda.tuple.Tuple.tuple;

public class JOOLTest {
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
        //given
        Stream<Integer> left = Arrays.asList(1, 2, 4).stream();
        Stream<Integer> right = Arrays.asList(1, 2, 3).stream();

        //when
        List<Integer> rightCollected = right.collect(Collectors.toList());
        List<Integer> collect = left.filter(rightCollected::contains).collect(Collectors.toList());

        //then
        assertEquals(collect, Arrays.asList(1, 2));
    }

    @Test
    public void givenSeq_whenJoin_shouldHaveElementsFromBothSeq() {
        assertEquals(
                Seq.of(1, 2, 4).innerJoin(Seq.of(1, 2, 3), (a, b) -> a == b).toList(),
                Arrays.asList(tuple(1, 1), tuple(2, 2))
        );


        assertEquals(
                Seq.of(1, 2, 4).leftOuterJoin(Seq.of(1, 2, 3), (a, b) -> a == b).toList(),
                Arrays.asList(tuple(1, 1), tuple(2, 2), tuple(4, null))
        );

        assertEquals(
                Seq.of(1, 2, 4).rightOuterJoin(Seq.of(1, 2, 3), (a, b) -> a == b).toList(),
                Arrays.asList(tuple(1, 1), tuple(2, 2), tuple(null, 3))
        );

        assertEquals(
                Seq.of(1, 2).crossJoin(Seq.of("A", "B")).toList(),
                Arrays.asList(tuple(1, "A"), tuple(1, "B"), tuple(2, "A"), tuple(2, "B"))
        );
    }

    @Test
    public void givenSeq_whenManipulateSeq_seqShouldHaveNewElementsInIt() {
        assertEquals(
                Seq.of(1, 2, 3).cycle().limit(9).toList(),
                Arrays.asList(1, 2, 3, 1, 2, 3, 1, 2, 3)
        );

        assertEquals(
                Seq.of(1, 2, 3).duplicate().map((first, second) -> tuple(first.toList(), second.toList())),
                tuple(Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3))
        );

        assertEquals(
                Seq.of(1, 2, 3, 4).intersperse(0).toList(),
                Arrays.asList(1, 0, 2, 0, 3, 0, 4)
        );

        assertEquals(
                Seq.of(1, 2, 3, 4, 5).shuffle().toList().size(),
                5
        );

        assertEquals(
                Seq.of(1, 2, 3, 4).partition(i -> i > 2).map((first, second) -> tuple(first.toList(), second.toList())),
                tuple(Arrays.asList(3, 4), Arrays.asList(1, 2))

        );

        assertEquals(
                Seq.of(1, 2, 3, 4).reverse().toList(),
                Arrays.asList(4, 3, 2, 1)
        );
    }

    @Test
    public void givenSeq_whenGroupByAndFold_shouldReturnProperSeq() {

        Map<Integer, List<Integer>> expectedAfterGroupBy = new HashMap<>();
        expectedAfterGroupBy.put(1, Arrays.asList(1, 3));
        expectedAfterGroupBy.put(0, Arrays.asList(2, 4));

        assertEquals(
                Seq.of(1, 2, 3, 4).groupBy(i -> i % 2),
                expectedAfterGroupBy
        );


        assertEquals(
                Seq.of("a", "b", "c").foldLeft("!", (u, t) -> u + t),
                "!abc"
        );


        assertEquals(
                Seq.of("a", "b", "c").foldRight("!", (t, u) -> t + u),
                "abc!"
        );
    }

    @Test
    public void givenSeq_whenUsingSeqWhile_shouldBehaveAsWhileLoop() {

        assertEquals(
                Seq.of(1, 2, 3, 4, 5).skipWhile(i -> i < 3).toList(),
                Arrays.asList(3, 4, 5)
        );

        assertEquals(
                Seq.of(1, 2, 3, 4, 5).skipUntil(i -> i == 3).toList(),
                Arrays.asList(3, 4, 5)
        );
    }

    @Test
    public void givenSeq_whenZip_shouldHaveZippedSeq() {

        assertEquals(
                Seq.of(1, 2, 3).zip(Seq.of("a", "b", "c")).toList(),
                Arrays.asList(tuple(1, "a"), tuple(2, "b"), tuple(3, "c"))
        );

        assertEquals(
                Seq.of(1, 2, 3).zip(Seq.of("a", "b", "c"), (x, y) -> x + ":" + y).toList(),
                Arrays.asList("1:a", "2:b", "3:c")
        );


        assertEquals(
                Seq.of("a", "b", "c").zipWithIndex().toList(),
                Arrays.asList(tuple("a", 0L), tuple("b", 1L), tuple("c", 2L))
        );
    }


    public Integer methodThatThrowsChecked(String arg) throws Exception {
        return arg.length();
    }

    @Test
    public void givenOperationThatThrowsCheckedException_whenExecuteAndNeedToWrapCheckedIntoUnchecked_shouldPass() {
        //when
        List<Integer> collect = Arrays.asList("a", "b", "c").stream().map(elem -> {
            try {
                return methodThatThrowsChecked(elem);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        //then
        assertEquals(
                collect,
                Arrays.asList(1, 1, 1)
        );
    }


    @Test
    public void givenOperationThatThrowsCheckedException_whenExecuteUsingUncheckedFuction_shouldPass() {
        //when
        List<Integer> collect = Arrays.asList("a", "b", "c").stream()
                .map(Unchecked.function(elem -> methodThatThrowsChecked(elem)))
                .collect(Collectors.toList());

        //then
        assertEquals(
                collect,
                Arrays.asList(1, 1, 1)
        );
    }

}
