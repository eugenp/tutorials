package com.baeldung.checkiflistcontainsenum;

import java.util.*;

import org.junit.Assert;
import org.junit.Test;

public class CheckIfListContainsEnumUnitTest {
    private final List<Map<String, Object>> data = new ArrayList<>();
    public boolean containsDeveloper = false;
    List<Position> list = Arrays.asList(Position.values());

    public CheckIfListContainsEnumUnitTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("Name", "John");
        map.put("Age", 25);
        map.put("Position", Position.DEVELOPER);

        data.add(map);
    }


    @Test
    public void givenDataList_whenUsingLoop_thenCheckIfListContainsEnum() {
        for (Map<String, Object> entry : data) {
            Object positionValue = entry.get("Position");

            for (Position position : Position.values()) {
                if (positionValue.equals(position)) {
                    containsDeveloper = true;
                    break;
                }
            }

            if (containsDeveloper) {
                break;
            }
        }

        Assert.assertTrue(containsDeveloper);
    }

    @Test
    public void givenDataList_whenUsingStream_thenCheckIfListContainsEnum() {
        containsDeveloper = data.stream()
                .map(entry -> entry.get("Position"))
                .anyMatch(position -> Arrays.asList(Position.values()).contains(position));

        Assert.assertTrue(containsDeveloper);
    }

    @Test
    public void givenDataList_whenUsingDisjointMethod_thenCheckIfListContainsEnum() {
        List<Position> positionValues = data.stream()
                .map(entry -> (Position) entry.get("Position"))
                .toList();

        boolean containsDeveloper = !Collections.disjoint(list, positionValues);
        Assert.assertTrue(containsDeveloper);
    }


    public enum Position {
        DEVELOPER("Dev"), MANAGER("Mgr"), ANALYST("Analyst");

        private final String value;

        Position(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
