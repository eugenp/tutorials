package com.baeldung.trifunction;

import io.vavr.Function3;

public class VavrFunction3Example {

    public static Function3<Integer, Integer, Integer, Integer> multiplyThenAdd = (x, y, z) -> x * y + z;

    public static Function3<Integer, Integer, Integer, Integer> multiplyThenAddThenDivideByTen = multiplyThenAdd.andThen(x -> x / 10);

}
