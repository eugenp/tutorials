package com.baeldung.folding;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Calculate a hash value for the strings using the folding technique.
 * 
 * The implementation serves only to the illustration purposes and is far
 * from being the most efficient. 
 * 
 * @author A.Shcherbakov
 *
 */
public class FoldingHash {

    /**
     * Calculate the hash value of a given string.
     * 
     * @param str Assume it is not null
     * @param groupSize the group size in the folding technique
     * @param maxValue defines a max value that the hash may acquire (exclusive)     
     * @return integer value from 0 (inclusive) to maxValue (exclusive)
     */
    public int hash(String str, int groupSize, int maxValue) {
        final int[] codes = this.toAsciiCodes(str);
        return IntStream.range(0, str.length())
            .filter(i -> i % groupSize == 0)
            .mapToObj(i -> extract(codes, i, groupSize))
            .map(block -> concatenate(block))
            .reduce(0, (a, b) -> (a + b) % maxValue);
    }

    /**
     * Returns a new array of given length whose elements are take from 
     * the original one starting from the offset.
     *  
     * If the original array has not enough elements, the returning array will contain 
     * element from the offset till the end of the original array.
     *  
     * @param numbers original array. Assume it is not null.
     * @param offset index of the element to start from. Assume it is less than the size of the array 
     * @param length max size of the resulting array
     * @return
     */
    public int[] extract(int[] numbers, int offset, int length) {
        final int defect = numbers.length - (offset + length);
        final int s = defect < 0 ? length + defect : length;
        int[] result = new int[s];
        for (int index = 0; index < s; index++) {
            result[index] = numbers[index + offset];
        }
        return result;
    }

    /**
     * Concatenate the numbers into a single number as if they were strings.
     * Assume that the procedure does not suffer from the overflow.
     * @param numbers integers to concatenate
     * @return
     */
    public int concatenate(int[] numbers) {
        final String merged = IntStream.of(numbers)
            .mapToObj(number -> "" + number)
            .collect(Collectors.joining());
        return Integer.parseInt(merged, 10);
    }

    /**
     * Convert the string into its characters' ASCII codes.
     * @param str input string
     * @return
     */
    private int[] toAsciiCodes(String str) {
        return str.chars()
            .toArray();
    }
}
