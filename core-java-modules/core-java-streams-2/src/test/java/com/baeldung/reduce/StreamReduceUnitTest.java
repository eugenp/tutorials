package com.baeldung.reduce;

import com.baeldung.reduce.entities.User;
import com.baeldung.reduce.utilities.NumberUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamReduceUnitTest {

    @Test
    public void givenIntegerList_whenReduceWithSumAccumulatorLambda_thenCorrect() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        int result = numbers.stream().reduce(0, (subtotal, element) -> subtotal + element);
        assertThat(result).isEqualTo(21);
    }

    @Test
    public void givenIntegerList_whenReduceWithSumAccumulatorMethodReference_thenCorrect() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        int result = numbers.stream().reduce(0, Integer::sum);
        assertThat(result).isEqualTo(21);
    }

    @Test
    public void givenStringList_whenReduceWithConcatenatorAccumulatorLambda_thenCorrect() {
        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        String result = letters.stream().reduce("", (partialString, element) -> partialString + element);
        assertThat(result).isEqualTo("abcde");
    }

    @Test
    public void givenStringList_whenReduceWithConcatenatorAccumulatorMethodReference_thenCorrect() {
        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        String result = letters.stream().reduce("", String::concat);
        assertThat(result).isEqualTo("abcde");
    }

    @Test
    public void givenStringList_whenReduceWithUppercaseConcatenatorAccumulator_thenCorrect() {
        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        String result = letters.stream().reduce("", (partialString, element) -> partialString.toUpperCase() + element.toUpperCase());
        assertThat(result).isEqualTo("ABCDE");
    }

    @Test
    public void givenUserList_whenReduceWithAgeAccumulatorAndSumCombiner_thenCorrect() {
        List<User> users = Arrays.asList(new User("John", 30), new User("Julie", 35));
        int result = users.stream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
        assertThat(result).isEqualTo(65);
    }

    @Test
    public void givenStringList_whenReduceWithParallelStream_thenCorrect() {
        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        String result = letters.parallelStream().reduce("", String::concat);
        assertThat(result).isEqualTo("abcde");
    }

    @Test
    public void givenNumberUtilsClass_whenCalledDivideListElements_thenCorrect() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        assertThat(NumberUtils.divideListElements(numbers, 1)).isEqualTo(21);
    }

    @Test
    public void givenNumberUtilsClass_whenCalledDivideListElementsWithExtractedTryCatchBlock_thenCorrect() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        assertThat(NumberUtils.divideListElementsWithExtractedTryCatchBlock(numbers, 1)).isEqualTo(21);
    }

    @Test
    public void givenStream_whneCalleddivideListElementsWithApplyFunctionMethod_thenCorrect() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        assertThat(NumberUtils.divideListElementsWithApplyFunctionMethod(numbers, 1)).isEqualTo(21);
    }
}
