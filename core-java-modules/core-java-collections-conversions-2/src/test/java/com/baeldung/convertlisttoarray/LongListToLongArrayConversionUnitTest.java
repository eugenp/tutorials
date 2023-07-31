package com.baeldung.convertlisttoarray;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.primitives.Longs;

public class LongListToLongArrayConversionUnitTest {

    private List<Long> list;

    @Before
    public void setUp() {
        list = Arrays.asList(1L, 2L, 3L, 4L, 5L);
    }

    @Test
    public void givenALongList_whenConvertWithListToArray_thenReturnLongArray() {

        // Init an array with the same size as the list to convert
        Long[] arrayWithSettedSize = new Long[list.size()];
        arrayWithSettedSize = list.toArray(arrayWithSettedSize);
        assertTrue(list.size() == arrayWithSettedSize.length);

        // Init an empty array
        Long[] arrayWithNoSettedSize = new Long[0];
        arrayWithNoSettedSize = list.toArray(arrayWithNoSettedSize);
        assertTrue(list.size() == arrayWithNoSettedSize.length);
    }

    @Test
    public void givenALongList_whenConvertWithLongsToArray_thenReturnLongArray() {
        // Convertion using Guava library Longs.toArray() method
        long[] array = Longs.toArray(list);
        assertTrue(compareListWithArray(list, array));
    }

    @Test
    public void givenALongList_whenConvertWithStreamMapToLong_thenReturnLongArray() {
        // Using mapToLong() - lambda expression
        long[] arrayUsingLambda = list.stream()
          .mapToLong(l -> l)
          .toArray();
        assertTrue(compareListWithArray(list, arrayUsingLambda));

        // Using mapToLong() - method reference
        long[] arrayUsingMethodReference = list.stream()
          .mapToLong(Long::longValue)
          .toArray();
        assertTrue(compareListWithArray(list, arrayUsingMethodReference));
    }

    public static boolean compareListWithArray(List<Long> list, long[] array) {
        // Check if the sizes of the array and list are equal
        if (array.length != list.size()) {
            return false;
        }

        // Compare each element of the array with the corresponding element in the list
        for (int i = 0; i < array.length; i++) {
            // Convert Long to long for comparison
            if (array[i] != list.get(i)) {
                return false;
            }
        }

        return true;
    }
}
