package com.baeldung.print2darrays;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.stream.SystemOut;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SystemStubsExtension.class)
public class Print2DArraysUnitTest {

    @SystemStub
    private SystemOut systemOut;

    @Test
    void whenPrint2dArrayUsingNestedLoop_thenLinesAreWrittenToSystemOut() {
        int[][] myArray = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        for (int i = 0; i < myArray.length; i++) {
            for (int j = 0; j < myArray[i].length; j++) {
                System.out.print(myArray[i][j] + " ");
            }
        }
        String expectedOutput = "1 2 3 4 5 6 7 8 9 ";
        assertThat(systemOut.getLines()).containsExactly(expectedOutput);
    }

    @Test
    public void whenPrint2dArrayUsingStreams_thenLinesAreWrittenToSystemOut() {
        int[][] myArray = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        Arrays.stream(myArray)
            .flatMapToInt(Arrays::stream)
            .forEach(num -> System.out.print(num + " "));
        String expectedOutput = "1 2 3 4 5 6 7 8 9 ";
        assertThat(systemOut.getLines()).containsExactly(expectedOutput);
    }

    @Test
    public void whenPrint2dArrayUsingDeepToString_thenLinesAreWrittenToSystemOut() {
        int[][] myArray = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        System.out.println(Arrays.deepToString(myArray));
        String expectedOutput = "[[1, 2, 3], [4, 5, 6], [7, 8, 9]]";
        assertThat(systemOut.getLines()).containsExactly(expectedOutput);
    }

    @Test
    public void whenPrint2dArrayUsingArrayToString_thenLinesAreWrittenToSystemOut() {
        int[][] myArray = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        for (int[] row : myArray) {
            System.out.print(Arrays.toString(row));
        }
        String expectedOutput = "[1, 2, 3][4, 5, 6][7, 8, 9]";
        assertThat(systemOut.getLines()).containsExactly(expectedOutput);
    }
}
