package org.baeldung.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.collections4.TransformerUtils;
import org.junit.Test;

public class MapUtilsTest {

    @Test
    public void whenCreateMapFrom2DArray_THEN_MUST_CREATE_Map() {
        Map colorMap = MapUtils.putAll(new HashMap(), new String[][] {
            {"RED", "#FF0000"},
            {"GREEN", "#00FF00"},
            {"BLUE", "#0000FF"}
        });

        assertNotNull(colorMap);
    }

    @Test
    public void whenCreateMapFrom1DArray_THEN_MUST_CREATE_AND_USE_Map() {
        Map colorMap = MapUtils.putAll(new HashMap(), new String[] {
            "RED", "#FF0000",
            "GREEN", "#00FF00",
            "BLUE", "#0000FF"
        });
        
        assertNotNull(colorMap);
        
        MapUtils.verbosePrint(System.out, "Optional Label", colorMap);
        
        String defaultColorStr = "COLOR_NOT_FOUND";
        
        String color1 = MapUtils.getString(colorMap, "BLACK", defaultColorStr);
        
        String color2 = MapUtils.getString(null, "RED", defaultColorStr);
        
        assertEquals(color1, defaultColorStr);
        assertEquals(color2, defaultColorStr);
        
        Map<String, String> invColorMap = MapUtils.invertMap(colorMap);
        
        assertNotNull(invColorMap);
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void whenCreateFixedSizedMapAndAdd_THEN_MUST_THROW_Exception() {
        Map rgbMap = MapUtils.fixedSizeMap(MapUtils.putAll(
            new HashMap(),
            new String[] {
              "RED", "#FF0000",
              "GREEN", "#00FF00",
              "BLUE", "#0000FF"
          }));
        
        rgbMap.put("ORANGE", "#FFA500");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void whenCreatePredicatedMapOfUniqueValuesAndAddDuplicate_THEN_MUST_THROW_Exception() {
        Map colorMap = MapUtils.putAll(new HashMap(), new String[] {
                "RED", "#FF0000",
                "GREEN", "#00FF00",
                "BLUE", "#0000FF"
            });
        
        Map<String, String> uniqValuesMap 
        = MapUtils.predicatedMap(colorMap, null, PredicateUtils.uniquePredicate());
        
        uniqValuesMap.put("NEW_RED", "#FF0000");
    }

    @Test
    public void whenCreateLazyMap_THEN_MUST_CREATE_Map() {
        Map<Integer, String> intStrMap = MapUtils.lazyMap(
            new HashMap<Integer, String>(),
            TransformerUtils.stringValueTransformer());
    }
}
