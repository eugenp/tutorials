package com.baeldung.array;

import com.google.common.primitives.ImmutableIntArray;
import com.google.common.primitives.Ints;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.util.Arrays;
import java.util.List;

public class PrimitiveCollections {

    public static void main(String[] args) {

        int[] primitives = new int[] {5, 10, 0, 2};

        //guavaPrimitives(primitives);

        trovePrimitives(primitives);
    }

    private static void trovePrimitives(int[] primitives) {
        TIntList tList = new TIntArrayList(primitives);
        tList.reverse();
        System.out.println(tList);
        System.out.println(tList.size());
    }

    private static void guavaPrimitives(int[] primitives) {

        ImmutableIntArray list = ImmutableIntArray.builder().addAll(primitives).build();

        List<Integer> integers = Ints.asList(primitives);

        int[] primitive = Ints.toArray(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println(Arrays.toString(primitive));
    }
}
