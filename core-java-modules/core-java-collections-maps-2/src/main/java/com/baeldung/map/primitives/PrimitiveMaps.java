package com.baeldung.map.primitives;

import com.carrotsearch.hppc.IntLongHashMap;
import com.carrotsearch.hppc.IntLongScatterMap;

import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanSortedMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanSortedMaps;
import org.eclipse.collections.api.map.primitive.ImmutableIntIntMap;
import org.eclipse.collections.api.map.primitive.MutableIntIntMap;
import org.eclipse.collections.api.map.primitive.MutableObjectDoubleMap;
import org.eclipse.collections.impl.factory.primitive.IntIntMaps;
import org.eclipse.collections.impl.factory.primitive.ObjectDoubleMaps;

public class PrimitiveMaps {

    public static void main(String[] args) {

        hppcMap();
        eclipseCollectionsMap();
        fastutilMap();
    }

    private static void hppcMap() {
        IntLongHashMap intLongHashMap = new IntLongHashMap();
        intLongHashMap.put(25,1L);
        intLongHashMap.put(150,Long.MAX_VALUE);
        intLongHashMap.put(1,0L);
        
        intLongHashMap.get(150);

        IntLongScatterMap intLongScatterMap = new IntLongScatterMap();
        intLongScatterMap.put(1, 1L);
        intLongScatterMap.put(2, -2L);
        intLongScatterMap.put(1000,0L);    

        intLongScatterMap.get(1000);
    }

    private static void fastutilMap() {
        Int2BooleanMap int2BooleanMap = new Int2BooleanOpenHashMap();
        int2BooleanMap.put(1, true);
        int2BooleanMap.put(7, false);
        int2BooleanMap.put(4, true);

        boolean value = int2BooleanMap.get(1);

        Int2BooleanSortedMap int2BooleanSorted = Int2BooleanSortedMaps.EMPTY_MAP;
    }

    private static void eclipseCollectionsMap() {
        MutableIntIntMap mutableIntIntMap = IntIntMaps.mutable.empty();
        mutableIntIntMap.addToValue(1, 1);

        ImmutableIntIntMap immutableIntIntMap = IntIntMaps.immutable.empty();

        MutableObjectDoubleMap<String> dObject = ObjectDoubleMaps.mutable.empty();
        dObject.addToValue("price", 150.5);
        dObject.addToValue("quality", 4.4);
        dObject.addToValue("stability", 0.8);
    }


}
