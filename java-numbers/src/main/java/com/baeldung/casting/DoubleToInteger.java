package com.baeldung.casting;

public class DoubleToInteger {

    static Double value = 99999.999;

    public static void main(String[] args) {

        System.out.println(usingIntValue(value));

        System.out.println(usingMathRound(value));
        System.out.println(usingMathCeil(value));

        System.out.println(usingMathFloor(value));
        System.out.println(usingMathAbs(value));
    }

    public static Integer usingIntValue(Double value) {
        return value.intValue();
    }

    public static Integer usingMathRound(Double value) {
        return (int) Math.round(value);
    }

    public static Integer usingMathCeil(Double value) {
        return (int) Math.ceil(value);
    }

    public static Integer usingMathFloor(Double value) {
        return (int) Math.floor(value);
    }

    public static Integer usingMathAbs(Double value) {
        return (int) Math.abs(value);
    }

    public static Integer usingCast(Double value) {
        return (int) value.doubleValue();
    }

}
