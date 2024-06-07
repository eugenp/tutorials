package com.baeldung.onescomplement;

import java.math.BigInteger;

public class OnesComplement {
    public static int calculateOnesComplementUsingBitwiseNot(int num) {
        int onesComplement = ~num;
        return onesComplement;
    }

    public static int calculateOnesComplementUsingXOROperator(int num, int bitLength) {
        int mask = (1 << bitLength) - 1;
        // To handle negative value
        int extendedNum = num < 0 ? (1 << bitLength) + num : num;

        return extendedNum ^ mask;
    }

    public static BigInteger calculateOnesComplementUsingBigInteger(BigInteger num) {
        BigInteger onesComplement = num.not();
        return onesComplement;
    }
}
