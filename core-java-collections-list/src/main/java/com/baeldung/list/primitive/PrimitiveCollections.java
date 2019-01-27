package com.baeldung.list.primitive;

import com.google.common.primitives.ImmutableIntArray;
import com.google.common.primitives.Ints;
import gnu.trove.list.array.TIntArrayList;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.Arrays;
import java.util.List;

public class PrimitiveCollections {

    public static void main(String[] args) {

        int[] primitives = new int[] {5, 10, 0, 2};

        guavaPrimitives(primitives);

        TIntArrayList tList = new TIntArrayList(primitives);

        cern.colt.list.IntArrayList coltList = new cern.colt.list.IntArrayList(primitives);

        IntArrayList fastUtilList = new IntArrayList(primitives);

        System.out.println(tList);

        System.out.println(coltList);

        System.out.println(fastUtilList);
    }


    private static void guavaPrimitives(int[] primitives) {

        ImmutableIntArray immutableIntArray = ImmutableIntArray.builder().addAll(primitives).build();
        System.out.println(immutableIntArray);
    }
}
