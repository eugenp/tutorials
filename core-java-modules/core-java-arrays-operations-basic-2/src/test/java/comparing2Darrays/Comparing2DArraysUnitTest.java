package comparing2Darrays;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class Comparing2DArraysUnitTest {

    int[][] arr1 = { { 1, 2, 3 }, { 4, 5, 6 } };
    int[][] arr2 = { { 1, 2, 3 }, { 4, 5, 6 } };

    @Test
    public void naiveApproachTest(){
        Assertions.assertTrue(NaiveApproach.areArraysEqual(arr1, arr2));
    }

    @Test
    public void optimizedApproachTest(){
        Assertions.assertTrue(OptimizedApproach.areArraysEqual(arr1, arr2));
    }
}
