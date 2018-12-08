package com.baeldung.commons.collections;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.collections4.TransformerUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapWithSize.aMapWithSize;
import static org.hamcrest.collection.IsMapWithSize.anEmptyMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MapUtilsUnitTest {

    private String[][] color2DArray = new String[][] { { "RED", "#FF0000" }, { "GREEN", "#00FF00" }, { "BLUE", "#0000FF" } };
    private String[] color1DArray = new String[] { "RED", "#FF0000", "GREEN", "#00FF00", "BLUE", "#0000FF" };
    private Map<String, String> colorMap;

    @Before
    public void createMap() {
        this.colorMap = MapUtils.putAll(new HashMap<String, String>(), this.color2DArray);
    }

    @Test
    public void whenCreateMapFrom2DArray_theMapIsCreated() {
        this.colorMap = MapUtils.putAll(new HashMap<String, String>(), this.color2DArray);

        assertThat(this.colorMap, is(aMapWithSize(this.color2DArray.length)));

        assertThat(this.colorMap, hasEntry("RED", "#FF0000"));
        assertThat(this.colorMap, hasEntry("GREEN", "#00FF00"));
        assertThat(this.colorMap, hasEntry("BLUE", "#0000FF"));
    }

    @Test
    public void whenCreateMapFrom1DArray_theMapIsCreated() {
        this.colorMap = MapUtils.putAll(new HashMap<String, String>(), this.color1DArray);

        assertThat(this.colorMap, is(aMapWithSize(this.color1DArray.length / 2)));

        assertThat(this.colorMap, hasEntry("RED", "#FF0000"));
        assertThat(this.colorMap, hasEntry("GREEN", "#00FF00"));
        assertThat(this.colorMap, hasEntry("BLUE", "#0000FF"));
    }

    @Test
    public void whenVerbosePrintMap_thenMustPrintFormattedMap() {
        MapUtils.verbosePrint(System.out, "Optional Label", this.colorMap);
    }

    @Test
    public void whenGetKeyNotPresent_thenMustReturnDefaultValue() {
        String defaultColorStr = "COLOR_NOT_FOUND";
        String color = MapUtils.getString(this.colorMap, "BLACK", defaultColorStr);

        assertEquals(color, defaultColorStr);
    }

    @Test
    public void whenGetOnNullMap_thenMustReturnDefaultValue() {
        String defaultColorStr = "COLOR_NOT_FOUND";
        String color = MapUtils.getString(null, "RED", defaultColorStr);

        assertEquals(color, defaultColorStr);
    }

    @Test
    public void whenInvertMap_thenMustReturnInvertedMap() {
        Map<String, String> invColorMap = MapUtils.invertMap(this.colorMap);

        int size = invColorMap.size();
        Assertions.assertThat(invColorMap).hasSameSizeAs(colorMap).containsKeys(this.colorMap.values().toArray(new String[size])).containsValues(this.colorMap.keySet().toArray(new String[size]));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateFixedSizedMapAndAdd_thenMustThrowException() {
        Map<String, String> rgbMap = MapUtils.fixedSizeMap(MapUtils.putAll(new HashMap<String, String>(), this.color1DArray));

        rgbMap.put("ORANGE", "#FFA500");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddDuplicateToUniqueValuesPredicateMap_thenMustThrowException() {
        Map<String, String> uniqValuesMap = MapUtils.predicatedMap(this.colorMap, null, PredicateUtils.uniquePredicate());

        uniqValuesMap.put("NEW_RED", "#FF0000");
    }

    @Test
    public void whenCreateLazyMap_theMapIsCreated() {
        Map<Integer, String> intStrMap = MapUtils.lazyMap(new HashMap<Integer, String>(), TransformerUtils.stringValueTransformer());

        assertThat(intStrMap, is(anEmptyMap()));

        intStrMap.get(1);
        intStrMap.get(2);
        intStrMap.get(3);

        assertThat(intStrMap, is(aMapWithSize(3)));
    }
}
