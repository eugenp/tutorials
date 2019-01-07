package com.baeldung.numberofdigits;

/**
 * @author zn.wang
 */
public class NumberOfDigits {

    /**
     * 基于字符串的解决方案
     * @param number
     * @return
     */
    public int stringBasedSolution(int number) {
        int length = String.valueOf(number).length();
        return length;
    }

    /**
     * 对数的方法
     * @param number
     * @return
     */
    public int logarithmicApproach(int number) {
        int length = (int) Math.log10(number) + 1;
        return length;
    }

    /**
     * 重复的乘
     * @param number
     * @return
     */
    public int repeatedMultiplication(int number) {
        int length = 0;
        long temp = 1;
        while(temp <= number) {
            length++;
            temp *= 10;
        }
        return length;
    }

    /**
     * 移位操作
     * @param number
     * @return
     */
    public int shiftOperators(int number) {
        int length = 0;
        long temp = 1;
        while(temp <= number) {
            length++;
            temp = (temp << 3) + (temp << 1);
        }
        return length;
    }

    /**
     * 除以2的幂
     * @param number
     * @return
     */
    public int dividingWithPowersOf2(int number) {
        int length = 1;
        if (number >= 100000000) {
            length += 8;
            number /= 100000000;
        }
        if (number >= 10000) {
            length += 4;
            number /= 10000;
        }
        if (number >= 100) {
            length += 2;
            number /= 100;
        }
        if (number >= 10) {
            length += 1;
        }
        return length;
    }

    /**
     * 分而治之
     * @param number
     * @return
     */
    public int divideAndConquer(int number) {
        if (number < 100000){
            // 5 digits or less
            if (number < 100){
                // 1 or 2
                if (number < 10){
                    return 1;
                }
                else{
                    return 2;
                }
            }
            else{
                // 3 to 5 digits
                if (number < 1000){
                    return 3;
                }
                else{
                    // 4 or 5 digits
                    if (number < 10000) {
                        return 4;
                    }
                    else{
                        return 5;
                    }
                }
            }
        }
        else {
            // 6 digits or more
            if (number < 10000000) {
                // 6 or 7 digits
                if (number < 1000000) {
                    return 6;
                }
                else{
                    return 7;
                }
            } else {
                // 8 to 10 digits
                if (number < 100000000){
                    return 8;
                }
                else {
                    // 9 or 10 digits
                    if (number < 1000000000){
                        return 9;
                    }
                    else{
                        return 10;
                    }
                }
            }
        }
    }
}
