package com.baeldung.onescomplement;

import java.math.BigInteger;

public class OnesComplement {

    public static int calculateOnesComplementUsingBitwiseNot(int num) {
        int onesComplement = ~num;
        return onesComplement;
    }

    public static int calculateOnesComplementUsingIntegerBinaryString(int num) {
        String binaryString = Integer.toBinaryString(num);
        StringBuilder onesComplementString = new StringBuilder();

        for (char bit : binaryString.toCharArray()) {
            onesComplementString.append(bit == '0' ? '1' : '0');
        }
        int onesComplement = Integer.parseInt(onesComplementString.toString(), 2);
        return onesComplement;
    }

    public static int calculateOnesComplementUsingXOROperator(int num, int bitLength) {
        int mask = (1 << bitLength) - 1;
        // To handle negative value
        int extendedNum = num < 0 ? (1 << bitLength) + num : num;

        int onesComplement = extendedNum ^ mask;
        return onesComplement;
    }

    public static BigInteger calculateOnesComplementUsingBigInteger(BigInteger num) {
        BigInteger onesComplement = num.not();
        return onesComplement;
    }
}
