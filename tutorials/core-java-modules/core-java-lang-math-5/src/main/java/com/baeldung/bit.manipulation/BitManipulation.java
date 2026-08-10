package com.baeldung.bit.manipulation;

public class BitManipulationUtils {


    public static int flipAllBits(int n) {
        return ~n;
    }


    public static int flipSignificantBits(int n) {
        if (n == 0) {
            return 0;
        }
        int bitLength = Integer.SIZE - Integer.numberOfLeadingZeros(n);
        int mask = (1 << bitLength) - 1;
        return n ^ mask;
    }


    public static int flipSignificantBitsUsingHighestOneBit(int n) {
        if (n == 0) {
            return 0;
        }
        int mask = (Integer.highestOneBit(n) << 1) - 1;
        return n ^ mask;
    }


    public static int flipSignificantBitsUsingNot(int n) {
        if (n == 0) {
            return 0;
        }
        int bitLength = Integer.SIZE - Integer.numberOfLeadingZeros(n);
        int mask = (1 << bitLength) - 1;
        return ~n & mask;
    }


    public static int flipBitsArithmetic(int n) {
        return -n - 1;
    }


    public static int flipBitsXorMinusOne(int n) {
        return n ^ -1;
    }
}
