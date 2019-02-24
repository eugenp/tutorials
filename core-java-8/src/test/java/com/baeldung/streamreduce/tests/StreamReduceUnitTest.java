package com.baeldung.streamreduce.tests;

import com.baeldung.streamreduce.entities.User;
import com.baeldung.streamreduce.utilities.NumberUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class StreamReduceUnitTest {

    @Test
    public void givenIntegerList_whenReduceWithSumAccumulatorLambda_thenCorrect() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        
        int result = numbers.stream().reduce(0, (a, b) -> a + b);
        
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
        
        String result = letters.stream().reduce("", (a, b) -> a + b);
        
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
        
        String result = letters.stream().reduce("", (a, b) -> a.toUpperCase() + b.toUpperCase());
        
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
    public void givenNumberUtilsClass_whenCalledDivideListElementsWithExtractedTryCatchBlockAndListContainsZero_thenCorrect() {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6);
        
        assertThat(NumberUtils.divideListElementsWithExtractedTryCatchBlock(numbers, 1)).isEqualTo(21);
    }
    
    @Test
    public void givenNumberUtilsClass_whenCalledDivideListElementsWithExtractedTryCatchBlockAndDividerIsZero_thenCorrect() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        
        assertThat(NumberUtils.divideListElementsWithExtractedTryCatchBlock(numbers, 0)).isEqualTo(0);
    }
    
    @Test
    public void givenStream_whneCalleddivideListElementsWithApplyFunctionMethod_thenCorrect() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        
        assertThat(NumberUtils.divideListElementsWithApplyFunctionMethod(numbers, 1)).isEqualTo(21);
    }
    
    @Test
    public void givenTwoStreams_whenCalledReduceOnParallelizedStream_thenFasterExecutionTime() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i <= 1000000; i++) {
            userList.add(new User("John" + i, i));
        }
        long currentTime1 = System.currentTimeMillis();
        userList.stream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
        long sequentialExecutionTime = System.currentTimeMillis() -currentTime1;
        long currentTime2 = System.currentTimeMillis();
        userList.parallelStream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
        long parallelizedExecutionTime = System.currentTimeMillis() - currentTime2;
        
        assertThat(parallelizedExecutionTime).isLessThan(sequentialExecutionTime);
    }
}
