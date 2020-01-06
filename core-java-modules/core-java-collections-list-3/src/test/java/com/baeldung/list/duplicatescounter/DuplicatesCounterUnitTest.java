package com.baeldung.list.duplicatescounter;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;

class DuplicatesCounterUnitTest {

    private static List<String> INPUT_LIST = Lists.list(
        "expect1",
        "expect2", "expect2",
        "expect3", "expect3", "expect3",
        "expect4", "expect4", "expect4", "expect4");

    @Test
    void givenInput_whenCountByClassicalLoop_thenGetResultMap() {
        Map<String, Long> result = DuplicatesCounter.countByClassicalLoop(INPUT_LIST);
        verifyResult(result);
    }

    @Test
    void givenInput_whenCountByForEachLoopWithGetOrDefault_thenGetResultMap() {
        Map<String, Long> result = DuplicatesCounter.countByForEachLoopWithGetOrDefault(INPUT_LIST);
        verifyResult(result);
    }

    @Test
    void givenInput_whenCountByForEachLoopWithMapCompute_thenGetResultMap() {
        Map<String, Long> result = DuplicatesCounter.countByForEachLoopWithMapCompute(INPUT_LIST);
        verifyResult(result);
    }

    @Test
    void givenInput_whenCountByForEachLoopWithMapMerge_thenGetResultMap() {
        Map<String, Long> result = DuplicatesCounter.countByForEachLoopWithMapMerge(INPUT_LIST);
        verifyResult(result);
    }

    @Test
    void givenInput_whenCountByStreamToMap_thenGetResultMap() {
        Map<String, Long> result = DuplicatesCounter.countByStreamToMap(INPUT_LIST);
        verifyResult(result);
    }

    @Test
    void givenInput_whenCountByStreamGroupBy_thenGetResultMap() {
        Map<String, Long> result = DuplicatesCounter.countByStreamGroupBy(INPUT_LIST);
        verifyResult(result);
    }

    private void verifyResult(Map<String, Long> resultMap) {
        assertThat(resultMap)
            .isNotEmpty().hasSize(4)
            .containsExactly(
                entry("expect1", 1L),
                entry("expect2", 2L),
                entry("expect3", 3L),
                entry("expect4", 4L));
    }
}