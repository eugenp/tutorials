package com.baeldung.threebool;

import java.util.Arrays;

public class ThreeBooleans {
    public static boolean twoOrMoreAreTrueByLoop(boolean a, boolean b, boolean c) {
        int count = 0;
        for (boolean i : new Boolean[] { a, b, c }) {
            count += i ? 1 : 0;
            if (count >= 2)
                return true;
        }
        return false;
    }

    public static boolean xOrMoreAreTrueByLoop(boolean[] booleans, int x) {
        int count = 0;
        for (boolean i : booleans) {
            count += i ? 1 : 0;
            if (count >= x)
                return true;
        }
        return false;
    }

    public static boolean twoOrMoreAreTrueBySum(boolean a, boolean b, boolean c) {
        return (a ? 1 : 0) + (b ? 1 : 0) + (c ? 1 : 0) >= 2;
    }

    public static boolean xOrMoreAreTrueBySum(Boolean[] booleans, int x) {
        return Arrays.stream(booleans).mapToInt(b -> Boolean.TRUE.equals(b) ? 1 : 0).sum() >= x;
    }

    public static boolean twoorMoreAreTrueByKarnaughMap(boolean a, boolean b, boolean c) {
        return (c && (a || b)) || (a && b);
    }

    public static boolean twoOrMoreAreTrueByOperators(boolean a, boolean b, boolean c) {
        return (a && b) || (a && c) || (b && c);
    }

    public static boolean twoOrMoreAreTrueByXor(boolean a, boolean b, boolean c) {
        return a ^ b ? c : a;
    }
}
