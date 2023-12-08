package com.baeldung.print2DArrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayPrinterTest {
    
    @SystemStub
    private SystemOut systemOut;

    @Test
    void whenPrint2D_thenUseNested() {
        int[][] myArray = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) { 
                System.out.print(array[i][j] + " "); 
            }
            System.out.println();
        }
        String expectedOutput = "1 2 3 \n4 5 6 \n7 8 9 \n";
        assertThat(systemOut.getLines()).containsExactly(expectedOutput);
    }

    @Test
    public void whenPrint2D_thenUseStream() {
        int[][] myArray = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Arrays.stream(array) .flatMapToInt(Arrays::stream) .forEach(num -> System.out.print(num + " "));
        String expectedOutput = "1 2 3 4 5 6 7 8 9";
        assertThat(systemOut.getLines()).containsExactly(expectedOutput);
    }

    @Test
    public void whenPrint2D_thenUseDeepToString() {
        int[][] myArray = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(Arrays.deepToString(myArray));
        String expectedOutput = "[[1, 2, 3], [4, 5, 6], [7, 8, 9]]";
        assertThat(systemOut.getLines()).containsExactly(expectedOutput);
    }

    @Test
    public void whenPrint2D_thenUseDeepToString() {
        int[][] myArray = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        for (int[] row : myArray) {
            System.out.println(Arrays.toString(row));
        }
        String expectedOutput = "[1, 2, 3]\n[4, 5, 6]\n[7, 8, 9]\n";
        assertThat(systemOut.getLines()).containsExactly(expectedOutput);
    }
}
