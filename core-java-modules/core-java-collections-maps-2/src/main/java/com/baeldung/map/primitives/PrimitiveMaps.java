package com.baeldung.map.primitives;

import com.carrotsearch.hppc.IntLongHashMap;
import com.carrotsearch.hppc.IntLongScatterMap;
import com.carrotsearch.hppc.IntObjectHashMap;
import com.carrotsearch.hppc.IntObjectMap;
import com.carrotsearch.hppc.IntObjectScatterMap;

import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanMaps;
import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanSortedMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanSortedMaps;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

import org.eclipse.collections.api.map.primitive.ImmutableIntIntMap;
import org.eclipse.collections.api.map.primitive.MutableIntIntMap;
import org.eclipse.collections.api.map.primitive.MutableObjectDoubleMap;
import org.eclipse.collections.impl.factory.primitive.IntIntMaps;
import org.eclipse.collections.impl.factory.primitive.ObjectDoubleMaps;

import static java.lang.String.format;

import java.math.BigDecimal;

public class PrimitiveMaps {

    public static void main(String[] args) {

        hppcMap();
        eclipseCollectionsMap();
        fastutilMap();
    }

    private static void hppcMap() {
        //Regular maps
        IntLongHashMap intLongHashMap = new IntLongHashMap();
        intLongHashMap.put(25,1L);
        intLongHashMap.put(150,Long.MAX_VALUE);
        intLongHashMap.put(1,0L);
        
        intLongHashMap.get(150);

        IntObjectMap<BigDecimal> intObjectMap = new IntObjectHashMap<BigDecimal>();
        intObjectMap.put(1, BigDecimal.valueOf(1));
        intObjectMap.put(2, BigDecimal.valueOf(2500));

        BigDecimal value = intObjectMap.get(2);

        //Scatter maps
        IntLongScatterMap intLongScatterMap = new IntLongScatterMap();
        intLongScatterMap.put(1, 1L);
        intLongScatterMap.put(2, -2L);
        intLongScatterMap.put(1000,0L);    

        intLongScatterMap.get(1000);

        IntObjectScatterMap<BigDecimal> intObjectScatterMap = new IntObjectScatterMap<BigDecimal>();
        intObjectScatterMap.put(1, BigDecimal.valueOf(1));
        intObjectScatterMap.put(2, BigDecimal.valueOf(2500));

        value = intObjectScatterMap.get(2);
    }


    private static void fastutilMap() {
        Int2BooleanMap int2BooleanMap = new Int2BooleanOpenHashMap();
        int2BooleanMap.put(1, true);
        int2BooleanMap.put(7, false);
        int2BooleanMap.put(4, true);

        boolean value = int2BooleanMap.get(1);

        //Lambda style iteration
        Int2BooleanMaps.fastForEach(int2BooleanMap, entry -> {
            System.out.println(String.format("Key: %d, Value: %b",entry.getIntKey(),entry.getBooleanValue()));
        });

        //Iterator based loop
        ObjectIterator<Int2BooleanMap.Entry> iterator = Int2BooleanMaps.fastIterator(int2BooleanMap);
        while(iterator.hasNext()) {
            Int2BooleanMap.Entry entry = iterator.next();
            System.out.println(String.format("Key: %d, Value: %b",entry.getIntKey(),entry.getBooleanValue()));

        }

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
