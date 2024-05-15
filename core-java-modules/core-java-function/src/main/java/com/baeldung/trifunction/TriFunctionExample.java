package com.baeldung.trifunction;

public class TriFunctionExample {

    public static TriFunction<Integer, Integer, Integer, Integer> multiplyThenAdd = (x, y, z) -> x * y + z;

    public static TriFunction<Integer, Integer, Integer, Integer> multiplyThenAddThenDivideByTen = multiplyThenAdd.andThen(x -> x / 10);

    public static TriFunction<Integer, String, Boolean, String> convertIntegerOrReturnStringDependingOnCondition = (myInt, myStr, myBool) -> {
        if (Boolean.TRUE.equals(myBool)) {
            return myInt != null ? myInt.toString() : "";
        } else {
            return myStr;
        }
    };

}
