package com.baeldung.onescomplement;

import java.math.BigInteger;

public class OnesComplement {

    public static int calculateOnesComplementUsingBitwiseNot(int num) {
        int onesComplement = ~num;
        System.out.println("1's complement of " + num + " is: " + onesComplement);
        return onesComplement;
    }

    public static int calculateOnesComplementUsingIntegerBinaryString(int num) {
        String binaryString = Integer.toBinaryString(num);
        StringBuilder onesComplementString = new StringBuilder();

        for (char bit : binaryString.toCharArray()) {
            onesComplementString.append(bit == '0' ? '1' : '0');
        }
        int onesComplement = Integer.parseInt(onesComplementString.toString(), 2);
        System.out.println("1's complement of " + num + " is: " + onesComplement);
        return onesComplement;
    }

    public static int calculateOnesComplementUsingXOROperator(int num, int bitLength) {
        int mask = (1 << bitLength) - 1;
        // To handle negative value
        int extendedNum = num < 0 ? (1 << bitLength) + num : num;

        int onesComplement = extendedNum ^ mask;
        System.out.println("1's complement of " + num + " (" + bitLength + "-bit) is: " + onesComplement);
        return onesComplement;
    }

    public static BigInteger calculateOnesComplementUsingBigInteger(BigInteger num) {
        BigInteger onesComplement = num.not();
        System.out.println("1's complement of " + num + " is: " + onesComplement);
        return onesComplement;
    }

    public static void main(String[] args) {
        //System.out.println(calculateOnesComplementUsingBitwiseNot(10));
       // System.out.println(calculateOnesComplementUsingIntegerBinaryString(10));
        System.out.println(calculateOnesComplementUsingXOROperator(-10, 32));
        //System.out.println(calculateOnesComplementUsingBigInteger(BigInteger.valueOf(10)));
    }
}
