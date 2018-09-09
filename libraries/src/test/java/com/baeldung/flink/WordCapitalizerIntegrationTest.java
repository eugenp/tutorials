package com.baeldung.flink;

import com.baeldung.flink.operator.WordsCapitalizer;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WordCapitalizerIntegrationTest {

    @Test
    public void givenDataSet_whenExecuteWordCapitalizer_thenReturnCapitalizedWords() throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        List<String> data = Arrays.asList("dog", "cat", "wolf", "pig");

         DataSet<String> testDataSet = env.fromCollection(data);


        List<String> dataProcessed = testDataSet
          .map(new WordsCapitalizer())
          .collect();

        List<String> testDataCapitalized = data.stream()
          .map(String::toUpperCase)
          .collect(Collectors.toList());

        Assert.assertEquals(testDataCapitalized, dataProcessed);
    }

}
