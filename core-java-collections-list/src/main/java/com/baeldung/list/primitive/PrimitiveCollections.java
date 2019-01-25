package com.baeldung.list.primitive;

import com.google.common.primitives.ImmutableIntArray;
import com.google.common.primitives.Ints;

import java.util.Arrays;
import java.util.List;

public class PrimitiveCollections {

    public static void main(String[] args) {

        int[] primitives = new int[] {5, 10, 0, 2};

        guavaPrimitives(primitives);
    }


    private static void guavaPrimitives(int[] primitives) {

        ImmutableIntArray immutableIntArray = ImmutableIntArray.builder().addAll(primitives).build();
        System.out.println(immutableIntArray);

        List<Integer> list = Ints.asList(primitives);

        int[] primitiveArray = Ints.toArray(list);

        int[] concatenated = Ints.concat(primitiveArray, primitives);

        System.out.println(Arrays.toString(concatenated));
    }
}
