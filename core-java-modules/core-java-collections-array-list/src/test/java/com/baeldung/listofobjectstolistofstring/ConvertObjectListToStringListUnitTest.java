package com.baeldung.listofobjectstolistofstring;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConvertObjectListToStringListUnitTest {

    @Test
    public void givenObjectList_whenForEachUsedToConvert_thenReturnSuccess() {
        List<String> outputList = new ArrayList<>(objectList().size());
        for (Object obj : objectList()) {
            outputList.add(Objects.toString(obj, null));
        }
        Assert.assertEquals(expectedStringList(), outputList);
    }

    @Test
    public void givenObjectList_whenUsingStreamsToConvert_thenReturnSuccess() {
        List<String> outputList;
        outputList = objectList().stream()
                .map((obj) -> Objects.toString(obj, null))
                .collect(Collectors.toList());
        Assert.assertEquals(expectedStringList(), outputList);

    }

    @Test
    public void givenObjectList_whenUsingGuavaTransform_thenReturnSuccess() {
        List<String> outputList;
        outputList = Lists.transform(objectList(), obj -> Objects.toString(obj, null));
        Assert.assertEquals(expectedStringList(), outputList);
    }

    private List<String> expectedStringList() {
        List<String> listOfStrings = new ArrayList<>();
        listOfStrings.add("1");
        listOfStrings.add("true");
        listOfStrings.add("hello");
        listOfStrings.add(Double.toString(273773.98));
        listOfStrings.add(null);
        Node node = new Node(2, 4);
        listOfStrings.add(node.toString());
        return listOfStrings;
    }

    private List<Object> objectList() {
        List<Object> listOfStrings = new ArrayList<>();
        listOfStrings.add(1);
        listOfStrings.add(true);
        listOfStrings.add("hello");
        listOfStrings.add(new Double(273773.98));
        listOfStrings.add(null);
        Node node = new Node(2, 4);
        listOfStrings.add(node);
        return listOfStrings;
    }
}
