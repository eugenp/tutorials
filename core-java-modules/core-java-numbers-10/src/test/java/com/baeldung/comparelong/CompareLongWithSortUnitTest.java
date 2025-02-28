package com.baeldung.comparelong;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class CompareLongWithSortUnitTest {

    @Test
    public void givenLongList_whenUseCollectionSortToCompare_thenReturnAsc() {
        List<Long> numbers = new ArrayList<>(Arrays.asList(500L, 200L, 800L));
        Collections.sort(numbers);
        assertEquals(Arrays.asList(200L, 500L, 800L), numbers);
    }

    @Test
    public void givenLongList_whenUseCollectionSortToCompareWithReverseComparator_thenReturnDsc() {
        List<Long> numbers = new ArrayList<>(Arrays.asList(500L, 200L, 800L));
        Collections.sort(numbers, Comparator.reverseOrder());
        assertEquals(Arrays.asList(800L, 500L, 200L), numbers);
    }

    @Test
    public void givenLongList_whenUseCustomComparator_thenReturnExpectedResult() {
        List<Long> numbers = new ArrayList<>(Arrays.asList(500L, 200L, 800L));
        long target = 400L;
        Collections.sort(numbers, (a, b) -> {
            long distanceA = Math.abs(a - target);
            long distanceB = Math.abs(b - target);
            return Long.compare(distanceA, distanceB);
        });
        assertEquals(Arrays.asList(500L, 200L, 800L), numbers);
    }

    @Test
    public void givenLongList_whenUseMultipleCriteriaToCompare_thenReturnExpectedResult() {
        List<Long> numbers = new ArrayList<>(Arrays.asList(-500L, 200L, -800L, 300L));
        Collections.sort(numbers, Comparator.comparingLong(n -> Math.abs((Long) n))
            .thenComparingLong(n -> (Long) n));

        assertEquals(Arrays.asList(200L, 300L, -500L, -800L), numbers);
    }

    @Test
    public void givenLongListWithNull_whenUsingNullsFirst_thenReturnNullAtFirstPosition() {
        List<Long> numbers = new ArrayList<>(Arrays.asList(500L, null, 200L, 800L));
        Collections.sort(numbers, Comparator.nullsFirst(Comparator.naturalOrder()));
        assertEquals(Arrays.asList(null, 200L, 500L, 800L), numbers);
    }

    @Test
    public void givenLongListWithNull_whenUsingNullsLast_thenReturnNullAtLastPosition() {
        List<Long> numbers = new ArrayList<>(Arrays.asList(500L, null, 200L, 800L));
        Collections.sort(numbers, Comparator.nullsLast(Comparator.naturalOrder()));
        assertEquals(Arrays.asList(200L, 500L, 800L, null), numbers);
    }

    @Test
    public void givenObjectWithLongAttribute_whenComparingObjectWithLongValue_thenReturnExpectedResult() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(500L));
        transactions.add(new Transaction(200L));
        transactions.add(new Transaction(800L));
        Collections.sort(transactions, Comparator.comparingLong(Transaction::getAmount));
        assertEquals(Arrays.asList(200L, 500L, 800L), transactions.stream()
            .map(Transaction::getAmount)
            .collect(Collectors.toList()));
    }

}

class Transaction {

    private Long amount;

    public Transaction(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }
}