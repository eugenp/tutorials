package com.baeldung.guava;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;

/**
 * guava-HashBiMap测试
 */
public class GuavaBiMapUnitTest {

    /**
     * 根据value查询key
     */
    @Test
    public void whenQueryByValue_returnsKey() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();
        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");

        final String countryCapitalName = capitalCountryBiMap.inverse().get("India");

        Assert.assertEquals("New Delhi", countryCapitalName);
    }

    /**
     * 根据已知的HashMap创建HashBiMap
     */
    @Test
    public void whenCreateBiMapFromExistingMap_returnsKey() {
        final Map<String, String> capitalCountryMap = new HashMap<String, String>(){{
                put("New Delhi", "India");
                put("Washingon, D.C.", "USA");
                put("Moscow", "Russia");
            }};
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create(capitalCountryMap);

        final String countryCapitalName = capitalCountryBiMap.inverse().get("India");

        Assert.assertEquals("New Delhi", countryCapitalName);
    }

    /**
     * 根据key查询，返回value
     */
    @Test
    public void whenQueryByKey_returnsValue() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");

        Assert.assertEquals("USA", capitalCountryBiMap.get("Washingon, D.C."));
    }


    /**
     * 当值放入重复的值时，会报IllegalArgumentException的错
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenSameValueIsPresent_throwsException() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();
        try {
            capitalCountryBiMap.put("New Delhi", "India");
            capitalCountryBiMap.put("Washingon, D.C.", "USA");
            capitalCountryBiMap.put("Moscow", "Russia");
            capitalCountryBiMap.put("Trump", "USA");
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }

    /**
     * 使用forcePut强制设置重复的value。
     * 但是需要注意的是，后设置的值会覆盖前面的值。
     */
    @Test
    public void givenSameValueIsPresent_whenForcePut_completesSuccessfully() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");
        capitalCountryBiMap.forcePut("Trump", "USA");

        Assert.assertEquals(3 , capitalCountryBiMap.size());
        Assert.assertEquals("USA", capitalCountryBiMap.get("Trump"));
        Assert.assertEquals("Trump", capitalCountryBiMap.inverse().get("USA"));
    }

    /**
     * 相同的键设置时，会进行覆盖
     */
    @Test
    public void whenSameKeyIsPresent_replacesAlreadyPresent() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");
        capitalCountryBiMap.put("Washingon, D.C.", "HongKong");

        Assert.assertEquals(3 , capitalCountryBiMap.size());
        Assert.assertEquals("HongKong", capitalCountryBiMap.get("Washingon, D.C."));
    }


    @Test
    public void whenUsingImmutableBiMap_allowsPutSuccessfully() {
        //不可变的BiMap
        final BiMap<String, String> capitalCountryBiMap = new ImmutableBiMap
                .Builder<String, String>()
                .put("New Delhi", "India")
                .put("Washingon, D.C.", "USA")
                .put("Moscow", "Russia")
                .build();

        Assert.assertEquals("USA", capitalCountryBiMap.get("Washingon, D.C."));
    }

    /**
     * 使用ImmutableBiMap后，不允许删除
     */
    @Test(expected = UnsupportedOperationException.class)
    public void whenUsingImmutableBiMap_doesntAllowRemove() {
        final BiMap<String, String> capitalCountryBiMap =
                new ImmutableBiMap
                        .Builder<String, String>()
                        .put("New Delhi", "India")
                        .put("Washingon, D.C.", "USA")
                        .put("Moscow", "Russia").build();
        try{
            capitalCountryBiMap.remove("New Delhi");
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }

    /**
     * 使用ImmutableBiMap只允许初始化的时候put，一旦初始化完毕就不允许put.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void whenUsingImmutableBiMap_doesntAllowPut() {
        final BiMap<String, String> capitalCountryBiMap = new ImmutableBiMap
                .Builder<String, String>()
                .put("New Delhi", "India")
                .put("Washingon, D.C.", "USA")
                .put("Moscow", "Russia")
                .build();
        try{
            capitalCountryBiMap.put("New York", "USA");
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }

    private enum Operation {
        // +
        ADD,
        // -
        SUBTRACT,
        // *
        MULTIPLY,
        // /
        DIVIDE
    }

    /**
     * EnumHashBiMap的使用
     */
    @Test
    public void whenUsingEnumAsKeyInMap_replacesAlreadyPresent() {
        final BiMap<Operation, String> operationStringBiMap = EnumHashBiMap.create(Operation.class);

        operationStringBiMap.put(Operation.ADD, "Add");
        operationStringBiMap.put(Operation.SUBTRACT, "Subtract");
        operationStringBiMap.put(Operation.MULTIPLY, "Multiply");
        operationStringBiMap.put(Operation.DIVIDE, "Divide");

        Assert.assertEquals("Divide", operationStringBiMap.get(Operation.DIVIDE));
        Assert.assertEquals("Multiply", operationStringBiMap.get(Operation.MULTIPLY));
    }
}