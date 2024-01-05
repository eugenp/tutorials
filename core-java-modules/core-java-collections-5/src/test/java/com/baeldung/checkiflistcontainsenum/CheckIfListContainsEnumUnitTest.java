package com.baeldung.checkiflistcontainsenum;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class CheckIfListContainsEnumUnitTest {
    private final List<Map<String, Object>> data = new ArrayList<>();

    public CheckIfListContainsEnumUnitTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("Name", "John");
        map.put("Age", 25);
        map.put("Position", Position.DEVELOPER);

        data.add(map);
    }

    @Test
    public void givenDataList_whenUsingLoop_thenCheckIfListContainsEnum() {
        boolean containsEnumValue = false;

        for (Map<String, Object> entry : data) {
            Object positionValue = entry.get("Position");
            if (Arrays.asList(Position.values()).contains(positionValue)) {
                containsEnumValue = true;
                break;
            }
        }

        Assert.assertTrue(containsEnumValue);
    }

    @Test
    public void givenDataList_whenUsingStream_thenCheckIfListContainsEnum() {
        boolean containsEnumValue = data.stream()
                .map(entry -> entry.get("Position"))
                .anyMatch(position -> Arrays.asList(Position.values()).contains(position));

        Assert.assertTrue(containsEnumValue);
    }

    @Test
    public void givenDataList_whenUsingDisjointMethod_thenCheckIfListContainsEnum() {
        List<Position> positionValues = data.stream()
                .map(entry -> (Position) entry.get("Position"))
                .toList();

        boolean containsEnumValue = !Collections.disjoint(Arrays.asList(Position.values()), positionValues);
        Assert.assertTrue(containsEnumValue);
    }

    public enum Position {
        DEVELOPER, MANAGER, ANALYST
    }
}
