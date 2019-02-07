package org.baeldung.codility;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CodilityTest1 {

    @Before
    public final void before() {
        //
    }

    // tests

    @Test
    public final void whenSolutionIsCalculated1_thenCorrect() {
        final int[] A = new int[] { 1, 4, 3, 3, 1, 2 };
        final int solution = solution(A);
        Assert.assertEquals(4, solution);
    }

    @Test
    public final void whenSolutionIsCalculated2_thenCorrect() {
        final int[] A = new int[] { 6, 4, 4, 6 };
        final int solution = solution(A);
        Assert.assertEquals(-1, solution);
    }

    @Test
    public final void whenSolutionIsCalculated3_thenCorrect() {
        final int[] A = new int[100000];
        final Random random = new Random();
        for (final int index : A) {
            A[index] = random.nextInt();
        }

        final long start = System.currentTimeMillis();
        final int solution = solution(A);
        final long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start));
        System.out.println(solution);
    }

    //

    final int solution(final int elements[]) {
        final Map<Integer, Integer> collector = new LinkedHashMap<Integer, Integer>();
        Integer currentValue = null;
        for (final int element : elements) {
            currentValue = collector.get(element);
            if (currentValue == null) {
                collector.put(element, 1);
            } else if (currentValue >= 1) {
                collector.put(element, ++currentValue);
            }
        }

        for (final Map.Entry<Integer, Integer> entry : collector.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }

        return -1;
    }

}
