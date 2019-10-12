package com.baeldung.array.operations;

import java.util.Arrays;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;

public class ArrayOperationsUnitTest {

    private Integer[] defaultObjectArray;
    private int[] defaultIntArray;
    private Integer[][] defaultJaggedObjectArray;
    private int[][] defaultJaggedIntArray;

    @BeforeEach
    public void setupDefaults() {
        defaultObjectArray = new Integer[] { 3, 5, 2, 5, 14, 4 };
        defaultIntArray = new int[] { 3, 5, 2, 5, 14, 4 };
        defaultJaggedObjectArray = new Integer[][] { { 1, 3 }, { 5 }, {} };
        defaultJaggedIntArray = new int[][] { { 1, 3 }, { 5 }, {} };
    }

    // Get the first and last item of an array
    @Test
    public void whenGetFirstObjectCalled_thenReturnFirstItemOfArray() {
        Integer output = ArrayOperations.getFirstObject(defaultObjectArray);

        assertThat(output).isEqualTo(3);
    }

    @Test
    public void whenGetFirstIntCalled_thenReturnFirstItemOfArray() {
        int output = ArrayOperations.getFirstInt(defaultIntArray);

        assertThat(output).isEqualTo(3);
    }

    @Test
    public void whenGetLastObjectCalled_thenReturnLastItemOfArray() {
        Integer output = ArrayOperations.getLastObject(defaultObjectArray);

        assertThat(output).isEqualTo(4);
    }

    @Test
    public void whenGetLastIntCalled_thenReturnLastItemOfArray() {
        int output = ArrayOperations.getLastInt(defaultIntArray);

        assertThat(output).isEqualTo(4);
    }

    // Append a new item to an array
    @Test
    public void whenAppendObject_thenReturnArrayWithExtraItem() {
        Integer[] output = ArrayOperations.appendObject(defaultObjectArray, 7);

        assertThat(output).endsWith(4, 7)
            .hasSize(7);
    }

    @Test
    public void whenAppendInt_thenReturnArrayWithExtraItem() {
        int[] output = ArrayOperations.appendInt(defaultIntArray, 7);
        int[] outputUsingUtils = ArrayOperations.appendIntWithUtils(defaultIntArray, 7);

        assertThat(output).endsWith(4, 7)
            .hasSize(7);
        assertThat(outputUsingUtils).endsWith(4, 7)
            .hasSize(7);
    }

    // Compare two arrays to check if they have the same elements
    @Test
    public void whenCompareObjectArrays_thenReturnBoolean() {
        Integer[] array2 = { 8, 7, 6 };
        Integer[] sameArray = { 3, 5, 2, 5, 14, 4 };
        boolean output = ArrayOperations.compareObjectArrays(defaultObjectArray, array2);
        boolean output2 = ArrayOperations.compareObjectArrays(defaultObjectArray, sameArray);

        assertThat(output).isFalse();
        assertThat(output2).isTrue();
    }

    @Test
    public void whenCompareIntArrays_thenReturnBoolean() {
        int[] array2 = { 8, 7, 6 };
        int[] sameArray = { 3, 5, 2, 5, 14, 4 };
        boolean output = ArrayOperations.compareIntArrays(defaultIntArray, array2);
        boolean output2 = ArrayOperations.compareIntArrays(defaultIntArray, sameArray);

        assertThat(output).isFalse();
        assertThat(output2).isTrue();
    }

    // Deep compare
    @Test
    public void whenDeepCompareObjectArrays_thenReturnBoolean() {
        Integer[][] sameArray = { { 1, 3 }, { 5 }, {} };
        Integer[][] array2 = { { 1, 3 }, { 5 }, { 3 } };
        boolean output = ArrayOperations.deepCompareObjectArrayUsingArrays(defaultJaggedObjectArray, array2);
        boolean output2 = ArrayOperations.deepCompareObjectArrayUsingArrays(defaultJaggedObjectArray, sameArray);
        // Because arrays are Objects, we could wrongly use the non-deep approach
        boolean outputUsingNonDeep = ArrayOperations.compareObjectArrays(defaultJaggedObjectArray, sameArray);

        assertThat(output).isFalse();
        assertThat(output2).isTrue();
        // This is not what we would expect!
        assertThat(outputUsingNonDeep).isFalse();
    }

    @Test
    public void whenDeepCompareIntArrays_thenReturnBoolean() {
        int[][] sameArray = { { 1, 3 }, { 5 }, {} };
        int[][] array2 = { { 1, 3 }, { 5 }, { 3 } };
        boolean output = ArrayOperations.deepCompareIntArrayUsingArrays(defaultJaggedIntArray, array2);
        boolean output2 = ArrayOperations.deepCompareIntArrayUsingArrays(defaultJaggedIntArray, sameArray);

        assertThat(output).isFalse();
        assertThat(output2).isTrue();
    }

    // Empty Check
    @Test
    public void whenIsEmptyObjectArray_thenReturnBoolean() {
        Integer[] array2 = {};
        Integer[] array3 = null;
        Integer[] array4 = { null, null, null };
        Integer[] array5 = { null };
        Integer[][] array6 = { {}, {}, {} };
        boolean output = ArrayOperations.isEmptyObjectArrayUsingUtils(defaultObjectArray);
        boolean output2 = ArrayOperations.isEmptyObjectArrayUsingUtils(array2);
        boolean output3 = ArrayOperations.isEmptyObjectArrayUsingUtils(array3);
        boolean output4 = ArrayOperations.isEmptyObjectArrayUsingUtils(array4);
        boolean output5 = ArrayOperations.isEmptyObjectArrayUsingUtils(array5);
        boolean output6 = ArrayOperations.isEmptyObjectArrayUsingUtils(array6);

        assertThat(output).isFalse();
        assertThat(output2).isTrue();
        assertThat(output3).isTrue();
        // Mind these edge cases!
        assertThat(output4).isFalse();
        assertThat(output5).isFalse();
        assertThat(output6).isFalse();
    }

    @Test
    public void whenIsEmptyIntArray_thenReturnBoolean() {
        int[] array2 = {};
        boolean output = ArrayOperations.isEmptyIntArrayUsingUtils(defaultIntArray);
        boolean output2 = ArrayOperations.isEmptyIntArrayUsingUtils(array2);

        assertThat(output).isFalse();
        assertThat(output2).isTrue();
    }

    // Remove Duplicates
    @Test
    public void whenRemoveDuplicateObjectArray_thenReturnArrayWithNoDuplicates() {
        Integer[] output = ArrayOperations.removeDuplicateObjects(defaultObjectArray);

        assertThat(output).containsOnlyOnce(5)
            .hasSize(5)
            .doesNotHaveDuplicates();
    }

    @Test
    public void whenRemoveDuplicateIntArray_thenReturnArrayWithNoDuplicates() {
        int[] output = ArrayOperations.removeDuplicateInts(defaultIntArray);

        assertThat(output).containsOnlyOnce(5)
            .hasSize(5)
            .doesNotHaveDuplicates();
    }

    // Remove Duplicates Preserving order
    @Test
    public void whenRemoveDuplicatePreservingOrderObjectArray_thenReturnArrayWithNoDuplicates() {
        Integer[] array2 = { 3, 5, 2, 14, 4 };
        Integer[] output = ArrayOperations.removeDuplicateWithOrderObjectArray(defaultObjectArray);

        assertThat(output).containsOnlyOnce(5)
            .hasSize(5)
            .containsExactly(array2);
    }

    @Test
    public void whenRemoveDuplicatePreservingOrderIntArray_thenReturnArrayWithNoDuplicates() {
        int[] array2 = { 3, 5, 2, 14, 4 };
        int[] output = ArrayOperations.removeDuplicateWithOrderIntArray(defaultIntArray);

        assertThat(output).containsOnlyOnce(5)
            .hasSize(5)
            .containsExactly(array2);
    }

    // Print
    @Test
    public void whenPrintObjectArray_thenReturnString() {
        String output = ArrayOperations.printObjectArray(defaultObjectArray);
        String jaggedOutput = ArrayOperations.printObjectArray(defaultJaggedObjectArray);
        // Comparing to Arrays output:
        String wrongArraysOutput = Arrays.toString(defaultJaggedObjectArray);
        String differentFormatArraysOutput = Arrays.toString(defaultObjectArray);
        // We should use Arrays.deepToString for jagged arrays
        String differentFormatJaggedArraysOutput = Arrays.deepToString(defaultJaggedObjectArray);

        assertThat(output).isEqualTo("{3,5,2,5,14,4}");
        assertThat(jaggedOutput).isEqualTo("{{1,3},{5},{}}");
        assertThat(differentFormatArraysOutput).isEqualTo("[3, 5, 2, 5, 14, 4]");
        assertThat(wrongArraysOutput).contains("[[Ljava.lang.Integer;@");
        assertThat(differentFormatJaggedArraysOutput).contains("[[1, 3], [5], []]");
    }

    @Test
    public void whenPrintIntArray_thenReturnString() {
        String output = ArrayOperations.printIntArray(defaultIntArray);
        String jaggedOutput = ArrayOperations.printIntArray(defaultJaggedIntArray);
        // Comparing to Arrays output:
        String wrongArraysOutput = Arrays.toString(defaultJaggedObjectArray);
        String differentFormatArraysOutput = Arrays.toString(defaultObjectArray);

        assertThat(output).isEqualTo("{3,5,2,5,14,4}");
        assertThat(jaggedOutput).isEqualTo("{{1,3},{5},{}}");
        assertThat(differentFormatArraysOutput).isEqualTo("[3, 5, 2, 5, 14, 4]");
        assertThat(wrongArraysOutput).contains("[[Ljava.lang.Integer;@");
    }

    // Box and unbox
    @Test
    public void whenUnboxObjectArray_thenReturnPrimitiveArray() {
        int[] output = ArrayOperations.unboxIntegerArray(defaultObjectArray);

        assertThat(output).containsExactly(defaultIntArray);
    }

    @Test
    public void henBoxPrimitiveArray_thenReturnObjectArray() {
        Integer[] output = ArrayOperations.boxIntArray(defaultIntArray);

        assertThat(output).containsExactly(defaultObjectArray);
    }

    // Map values
    @Test
    public void whenMapMultiplyingObjectArray_thenReturnMultipliedArray() {
        Integer[] multipliedExpectedArray = new Integer[] { 6, 10, 4, 10, 28, 8 };
        Integer[] output = ArrayOperations.mapObjectArray(defaultObjectArray, value -> value * 2, Integer.class);

        assertThat(output).containsExactly(multipliedExpectedArray);
    }

    @Test
    public void whenMapDividingObjectArray_thenReturnDividedArray() {
        Double[] multipliedExpectedArray = new Double[] { 1.5, 2.5, 1.0, 2.5, 7.0, 2.0 };
        Double[] output = ArrayOperations.mapObjectArray(defaultObjectArray, value -> value / 2.0, Double.class);

        assertThat(output).containsExactly(multipliedExpectedArray);
    }

    @Test
    public void whenMapIntArrayToString_thenReturnArray() {
        String[] expectedArray = new String[] { "Value: 3", "Value: 5", "Value: 2", "Value: 5", "Value: 14", "Value: 4" };
        String[] output = ArrayOperations.mapIntArrayToString(defaultIntArray);

        assertThat(output).containsExactly(expectedArray);
    }

    // Filter values
    @Test
    public void whenFilterObjectArray_thenReturnFilteredArray() {
        Integer[] multipliedExpectedArray = new Integer[] { 2, 14, 4 };
        Integer[] output = ArrayOperations.filterObjectArray(defaultObjectArray, value -> value % 2 == 0);

        assertThat(output).containsExactly(multipliedExpectedArray);
    }

    @Test
    public void whenFilterIntArray_thenReturnFilteredArray() {
        int[] expectedArray = new int[] { 2, 14, 4 };
        int[] output = ArrayOperations.filterIntArray(defaultIntArray, value -> (int) value % 2 == 0);

        assertThat(output).containsExactly(expectedArray);
    }

    // Insert between
    @Test
    public void whenInsertBetweenIntArrayToString_thenReturnNewArray() {
        int[] expectedArray = { 3, 5, 77, 88, 2, 5, 14, 4 };
        int[] output = ArrayOperations.insertBetweenIntArray(defaultIntArray, 77, 88);

        assertThat(output).containsExactly(expectedArray);
    }

    @Test
    public void whenInsertBetweenObjectArrayToString_thenReturnNewArray() {
        Integer[] expectedArray = { 3, 5, 77, 99, 2, 5, 14, 4 };
        Integer[] output = ArrayOperations.insertBetweenObjectArray(defaultObjectArray, 77, 99);

        assertThat(output).containsExactly(expectedArray);
    }

    // Shuffle between
    @Test
    public void whenShufflingIntArraySeveralTimes_thenAtLeastOneWithDifferentOrder() {
        int[] output = ArrayOperations.shuffleIntArray(defaultIntArray);
        int[] output2 = ArrayOperations.shuffleIntArray(defaultIntArray);
        int[] output3 = ArrayOperations.shuffleIntArray(defaultIntArray);
        int[] output4 = ArrayOperations.shuffleIntArray(defaultIntArray);
        int[] output5 = ArrayOperations.shuffleIntArray(defaultIntArray);
        int[] output6 = ArrayOperations.shuffleIntArray(defaultIntArray);

        Condition<int[]> atLeastOneArraysIsNotEqual = new Condition<int[]>("at least one output should be different (order-wise)") {
            @Override
            public boolean matches(int[] value) {
                return !Arrays.equals(value, output) || !Arrays.equals(value, output2) || !Arrays.equals(value, output3) || !Arrays.equals(value, output4) || !Arrays.equals(value, output5) || !Arrays.equals(value, output6);
            }
        };

        assertThat(defaultIntArray).has(atLeastOneArraysIsNotEqual);
    }

    @Test
    public void whenShufflingObjectArraySeveralTimes_thenAtLeastOneWithDifferentOrder() {
        Integer[] output = ArrayOperations.shuffleObjectArray(defaultObjectArray);
        Integer[] output2 = ArrayOperations.shuffleObjectArray(defaultObjectArray);
        Integer[] output3 = ArrayOperations.shuffleObjectArray(defaultObjectArray);
        Integer[] output4 = ArrayOperations.shuffleObjectArray(defaultObjectArray);
        Integer[] output5 = ArrayOperations.shuffleObjectArray(defaultObjectArray);
        Integer[] output6 = ArrayOperations.shuffleObjectArray(defaultObjectArray);

        Condition<Integer[]> atLeastOneArraysIsNotEqual = new Condition<Integer[]>("at least one output should be different (order-wise)") {
            @Override
            public boolean matches(Integer[] value) {
                return !Arrays.equals(value, output) || !Arrays.equals(value, output2) || !Arrays.equals(value, output3) || !Arrays.equals(value, output4) || !Arrays.equals(value, output5) || !Arrays.equals(value, output6);
            }
        };

        assertThat(defaultObjectArray).has(atLeastOneArraysIsNotEqual);
    }

    // Get random item
    @Test
    public void whenGetRandomFromIntArrayToString_thenReturnItemContainedInArray() {
        int output = ArrayOperations.getRandomFromIntArray(defaultIntArray);

        assertThat(defaultIntArray).contains(output);
    }

    @Test
    public void whenGetRandomFromObjectArrayToString_thenReturnItemContainedInArray() {
        Integer output = ArrayOperations.getRandomFromObjectArray(defaultObjectArray);

        assertThat(defaultObjectArray).contains(output);
    }

    @Test
    public void givenSourceArrayAndElement_whenAddElementUsingPureJavaIsInvoked_thenNewElementMustBeAdded() {
        Integer[] sourceArray = { 1, 2, 3, 4 };
        int elementToAdd = 5;

        Integer[] destArray = ArrayOperations.addElementUsingPureJava(sourceArray, elementToAdd);

        Integer[] expectedArray = { 1, 2, 3, 4, 5 };
        assertArrayEquals(expectedArray, destArray);
    }

    @Test
    public void whenInsertAnElementAtAGivenIndexCalled_thenShiftTheFollowingElementsAndInsertTheElementInArray() {
        int[] expectedArray = { 1, 4, 2, 3, 0 };
        int[] anArray = new int[4];
        anArray[0] = 1;
        anArray[1] = 2;
        anArray[2] = 3;
        int[] outputArray = ArrayOperations.insertAnElementAtAGivenIndex(anArray, 1, 4);

        assertThat(outputArray).containsExactly(expectedArray);
    }
}
