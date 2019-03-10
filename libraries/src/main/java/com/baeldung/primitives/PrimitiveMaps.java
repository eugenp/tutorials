package com.baeldung.primitives;

import cern.colt.map.AbstractIntDoubleMap;
import cern.colt.map.OpenIntDoubleHashMap;
import gnu.trove.map.TDoubleIntMap;
import gnu.trove.map.hash.TDoubleIntHashMap;
import it.unimi.dsi.fastutil.ints.*;
import org.eclipse.collections.api.map.primitive.*;
import org.eclipse.collections.impl.factory.primitive.*;

public class PrimitiveMaps {

    public static void main(String[] args) {

        eclipseCollectionsMap();
        troveMap();
        coltMap();
        fastutilMap();
    }

    private static void fastutilMap() {
        Int2BooleanMap int2BooleanMap = new Int2BooleanOpenHashMap();
        int2BooleanMap.put(1, true);

        Int2BooleanSortedMap int2BooleanSorted = Int2BooleanSortedMaps.EMPTY_MAP;
        int2BooleanSorted.putIfAbsent(7, true);
        int2BooleanSorted.putIfAbsent(1, true);
        int2BooleanSorted.putIfAbsent(4, true);
    }

    private static void coltMap() {
        AbstractIntDoubleMap map = new OpenIntDoubleHashMap();
        map.put(1, 4.5);
    }

    private static void eclipseCollectionsMap() {
        MutableObjectDoubleMap<String> doubleMap = ObjectDoubleMaps.mutable.empty();
        doubleMap.put("1", 1.0d);
        doubleMap.put("2", 2.0d);

        MutableObjectIntMap<String> booleanMap = ObjectIntMaps.mutable.empty();
        booleanMap.put("ok", 1);

        MutableIntIntMap mutableIntIntMap = IntIntMaps.mutable.empty();
        mutableIntIntMap.addToValue(1, 1);

        ImmutableIntIntMap immutableIntIntMap = IntIntMaps.immutable.empty();

        MutableObjectIntMap<String> intObject = ObjectIntMaps.mutable.empty();
        intObject.addToValue("price", 2);
    }

    private static void troveMap() {

        double[] doubles = new double[] {1.2, 4.5, 0.3};
        int[] ints = new int[] {1, 4, 0};

        TDoubleIntMap doubleIntMap = new TDoubleIntHashMap(doubles, ints);

        doubleIntMap.adjustValue(1.2, 1);
        doubleIntMap.adjustValue(4.5, 4);
        doubleIntMap.adjustValue(0.3, 0);

        System.out.println(doubleIntMap);
    }
}
