package com.baeldung.hashmaptoarraylist;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class HashMapToArrayListConverterUtilsUnitTest {

    private HashMap<Integer, String> hashMap;

    @Before
    public void beforeEach() {
        hashMap = new HashMap<>();
        hashMap.put(1, "AAA");
        hashMap.put(2, "BBB");
        hashMap.put(3, "CCC");
        hashMap.put(4, "DDD");
    }

    @Test
    public void givenAHashMap_whenConvertUsingConstructor_thenReturnArrayList() {
        ArrayList<String> myList = HashMapToArrayListConverterUtils.convertUsingConstructor(hashMap);

        assertThat(hashMap.values(), containsInAnyOrder(myList.toArray()));
    }

    @Test
    public void givenAHashMap_whenConvertUsingAddAllMethod_thenReturnArrayList() {
        ArrayList<String> myList = HashMapToArrayListConverterUtils.convertUsingAddAllMethod(hashMap);

        assertThat(hashMap.values(), containsInAnyOrder(myList.toArray()));
    }

    @Test
    public void givenAHashMap_whenConvertUsingForLoop_thenReturnArrayList() {
        ArrayList<String> myList = HashMapToArrayListConverterUtils.convertUsingForLoop(hashMap);

        assertThat(hashMap.values(), containsInAnyOrder(myList.toArray()));
    }

    @Test
    public void givenAHashMap_whenConvertUsingStreamApi_thenReturnArrayList() {
        ArrayList<String> myList = HashMapToArrayListConverterUtils.convertUsingStreamApi(hashMap);

        assertThat(hashMap.values(), containsInAnyOrder(myList.toArray()));
    }

    @Test
    public void givenAHashMap_whenConvertUsingGuava_thenReturnArrayList() {
        ArrayList<String> myList = HashMapToArrayListConverterUtils.convertUsingGuava(hashMap);

        assertThat(hashMap.values(), containsInAnyOrder(myList.toArray()));
    }

}
