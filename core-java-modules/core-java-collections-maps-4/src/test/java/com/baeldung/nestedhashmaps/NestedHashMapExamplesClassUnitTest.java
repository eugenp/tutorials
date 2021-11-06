package com.baeldung.nestedhashmaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsMapContaining;

public class NestedHashMapExamplesClassUnitTest {
    private MapsUtil mUtil = new MapsUtil();
    private List<String> batterList = new ArrayList<>();
    private List<Employee> listEmployee = new ArrayList<Employee>();
    private Map<String, Map<Integer, String>> actualBakedGoodsMap = new HashMap<>();
    private Map<Integer, Map<String, String>> actualEmployeeAddressMap = new HashMap<>();
    private Map<Integer, Map<Integer, Address>> actualEmployeeMap = new HashMap<>();

    @Test
    public void whenCreateNestedHashMap_thenNestedMap() {
        assertThat(mUtil.buildInnerMap(batterList), is(notNullValue()));
        Assert.assertEquals(actualBakedGoodsMap.keySet().size(), 2);
        Assert.assertThat(actualBakedGoodsMap, IsMapContaining.hasValue(equalTo(mUtil.buildInnerMap(batterList))));
    }

    private Map<Integer, Map<String, String>> setup() {
        Map<Integer, Map<String, String>> expectedMap = new HashMap<>();
        expectedMap.put(Integer.valueOf(100), new HashMap<String, String>() {
            {
                put("Misty Lanes", "Balin");
            }
        });
        expectedMap.put(Integer.valueOf(200), new HashMap<String, String>() {
            {
                put("Bag End", "Bilbo Baggins");
            }
        });
        expectedMap.put(Integer.valueOf(156), new HashMap<String, String>() {
            {
                put("Brambles Lane", "Bofur");
            }
        });
        expectedMap.put(Integer.valueOf(124), new HashMap<String, String>() {
            {
                put("Timbuktoo", "Thorin Oakenshield");
            }
        });
        expectedMap.put(Integer.valueOf(23), new HashMap<String, String>() {
            {
                put("Rivendell", "Elrond");
            }
        });

        return expectedMap;
    }

    @Test
    public void whenCreateNestedHashMapwithStreams_thenNestedMap() {

        Map<Integer, Map<String, String>> expectedMap = setup();

        assertThat(actualEmployeeAddressMap, equalTo(expectedMap));
    }

    @Test
    public void whenCompareTwoHashMapswithDifferenctValues_usingEquals_thenFail() {
        Map<String, Map<Integer, String>> outerBakedGoodsMap2 = new HashMap<>();
        outerBakedGoodsMap2.put("Eclair", mUtil.buildInnerMap(batterList));
        outerBakedGoodsMap2.put("Donut", mUtil.buildInnerMap(batterList));
        assertNotEquals(outerBakedGoodsMap2, actualBakedGoodsMap);

        Map<String, Map<Integer, String>> outerBakedGoodsMap3 = new HashMap<String, Map<Integer, String>>();
        outerBakedGoodsMap3.put("Cake", mUtil.buildInnerMap(batterList));
        batterList = new ArrayList<>();
        batterList = Arrays.asList("Banana", "Red Velvet", "Blackberry", "Passion fruit", "Kiwi");

        outerBakedGoodsMap3.put("Donut", mUtil.buildInnerMap(batterList));

        assertNotEquals(outerBakedGoodsMap2, actualBakedGoodsMap);

        listEmployee.clear();
        listEmployee.add(new Employee(1, new Address(500, "Timbuktoo"), "Thorin Oakenshield"));
        listEmployee.add(new Employee(2, new Address(600, "Misty Lanes"), "Balin"));
        listEmployee.add(new Employee(3, new Address(700, "Bramles Lane"), "Bofur"));
        listEmployee.add(new Employee(4, new Address(800, "Bag End"), "Bilbo Baggins"));
        listEmployee.add(new Employee(5, new Address(900, "Rivendell"), "Elrond"));

        Map<Integer, Map<String, String>> employeeAddressMap1 = mUtil.createNestedMapfromStream(listEmployee);

        Map<Integer, Map<Integer, Address>> employeeMap1 = mUtil.createNestedObjectMap(listEmployee);

        assertNotEquals(employeeAddressMap1, actualEmployeeAddressMap);

        assertNotEquals(employeeMap1, actualEmployeeMap);
    }

    @Test
    public void whencomparingDifferentObjectValuesUsingEquals_thenFail() {
        listEmployee.clear();
        listEmployee.add(new Employee(1, new Address(124, "Timbuktoo"), "Thorin Oakenshield"));
        listEmployee.add(new Employee(2, new Address(100, "Misty Lanes"), "Balin"));
        listEmployee.add(new Employee(3, new Address(156, "Brambles Lane"), "Bofur"));
        listEmployee.add(new Employee(4, new Address(200, "Bag End"), "Bilbo Baggins"));
        listEmployee.add(new Employee(5, new Address(23, "Rivendell"), "Elrond"));

        Map<Integer, Map<Integer, Object>> employeeMap1 = listEmployee.stream().collect(Collectors.groupingBy(
                (Employee emp) -> emp.getEmployeeId(),
                Collectors.toMap((Employee emp) -> emp.getAddress().getAddressId(), fEmpObj -> fEmpObj.getAddress())));

        assertNotSame(employeeMap1, actualEmployeeMap);
        assertNotEquals(employeeMap1, actualEmployeeMap);

        Map<Integer, Map<Integer, Address>> expectedMap = setupAddressObjectMap();
        assertNotSame(expectedMap, actualEmployeeMap);
        assertNotEquals(expectedMap, actualEmployeeMap);

    }

    @Test
    public void whenCompareTwoHashMapsUsingEquals_thenSuccess() {
        Map<String, Map<Integer, String>> outerBakedGoodsMap4 = new HashMap<>();
        outerBakedGoodsMap4.putAll(actualBakedGoodsMap);

        assertEquals(actualBakedGoodsMap, outerBakedGoodsMap4);

        Map<Integer, Map<Integer, Address>> employeeMap1 = new HashMap<>();
        employeeMap1.putAll(actualEmployeeMap);
        assertEquals(actualEmployeeMap, employeeMap1);
    }

    @Test
    public void whenAddElementinHashMaps_thenSuccess() {
        assertEquals(actualBakedGoodsMap.get("Cake").size(), 5);
        actualBakedGoodsMap.get("Cake").put(6, "Cranberry");
        assertEquals(actualBakedGoodsMap.get("Cake").size(), 6);
    }

    @Test
    public void whenDeleteElementinHashMaps_thenSuccess() {
        assertNotEquals(actualBakedGoodsMap.get("Cake").get(5), null);
        actualBakedGoodsMap.get("Cake").remove(5);
        assertEquals(actualBakedGoodsMap.get("Cake").get(5), null);

        actualBakedGoodsMap.put("Eclair", new HashMap<Integer, String>() {
            {
                put(1, "Dark Chocolate");
            }
        });

        assertNotEquals(actualBakedGoodsMap.get("Eclair").get(1), null);
        actualBakedGoodsMap.get("Eclair").remove(1);
        assertEquals(actualBakedGoodsMap.get("Eclair").get(1), null);

        actualBakedGoodsMap.put("Eclair", new HashMap<Integer, String>() {
            {
                put(1, "Dark Chocolate");
            }
        });

        assertNotEquals(actualBakedGoodsMap.get("Eclair"), null);
        actualBakedGoodsMap.remove("Eclair");
        assertEquals(actualBakedGoodsMap.get("Eclair"), null);
    }

    @Test
    public void whenFlattenMap_thenRemovesNesting() {

        Map<String, String> flattenedBakedGoodsMap = mUtil.flattenMap(actualBakedGoodsMap);
        assertThat(flattenedBakedGoodsMap, IsMapContaining.hasKey("Donut.2"));

        Map<String, String> flattenedEmployeeAddressMap = mUtil.flattenMap(actualEmployeeAddressMap);
        assertThat(flattenedEmployeeAddressMap, IsMapContaining.hasKey("200.Bag End"));
    }

    @Before
    public void buildMaps() {

        batterList = Arrays.asList("Mulberry", "Cranberry", "Blackberry", "Mixed fruit", "Orange");

        actualBakedGoodsMap.put("Cake", mUtil.buildInnerMap(batterList));

        batterList = new ArrayList<>();
        batterList = Arrays.asList("Candy", "Dark Chocolate", "Chocolate", "Jam filled", "Pineapple");

        actualBakedGoodsMap.put("Donut", mUtil.buildInnerMap(batterList));

        listEmployee.add(new Employee(1, new Address(124, "Timbuktoo"), "Thorin Oakenshield"));
        listEmployee.add(new Employee(2, new Address(100, "Misty Lanes"), "Balin"));
        listEmployee.add(new Employee(3, new Address(156, "Brambles Lane"), "Bofur"));
        listEmployee.add(new Employee(4, new Address(200, "Bag End"), "Bilbo Baggins"));
        listEmployee.add(new Employee(5, new Address(23, "Rivendell"), "Elrond"));

        actualEmployeeAddressMap = mUtil.createNestedMapfromStream(listEmployee);

        actualEmployeeMap = mUtil.createNestedObjectMap(listEmployee);

    }

    private Map<Integer, Map<Integer, Address>> setupAddressObjectMap() {

        Map<Integer, Map<Integer, Address>> expectedMap = new HashMap<>();

        expectedMap.put(1, new HashMap<Integer, Address>() {
            {
                put(124, new Address(124, "Timbuktoo"));
            }
        });
        expectedMap.put(2, new HashMap<Integer, Address>() {
            {
                put(100, new Address(100, "Misty Lanes"));
            }
        });
        expectedMap.put(3, new HashMap<Integer, Address>() {
            {
                put(156, new Address(156, "Brambles Lane"));
            }
        });
        expectedMap.put(4, new HashMap<Integer, Address>() {
            {
                put(200, new Address(200, "Bag End"));
            }
        });
        expectedMap.put(5, new HashMap<Integer, Address>() {
            {
                put(23, new Address(23, "Rivendell"));
            }
        });
        return expectedMap;
    }

}
