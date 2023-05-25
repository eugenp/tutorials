package com.baeldung.binarynumbers;

public class BinaryNumbers {

    /**
     * This method takes a decimal number and convert it into a binary number.
     * example:- input:10, output:1010
     *
     * @param decimalNumber
     * @return binary number
     */
    public Integer convertDecimalToBinary(Integer decimalNumber) {

        if (decimalNumber == 0) {
            return decimalNumber;
        }

        StringBuilder binaryNumber = new StringBuilder();
        Integer quotient = decimalNumber;

        while (quotient > 0) {

            int remainder = quotient % 2;
            binaryNumber.append(remainder);
            quotient /= 2;
        }

        binaryNumber = binaryNumber.reverse();
        return Integer.valueOf(binaryNumber.toString());
    }

    /**
     * This method takes a binary number and convert it into a decimal number.
     * example:- input:101, output:5
     *
     * @param binary number
     * @return decimal Number
     */
    public Integer convertBinaryToDecimal(Integer binaryNumber) {

        Integer decimalNumber = 0;
        Integer base = 1;

        while (binaryNumber > 0) {

            int lastDigit = binaryNumber % 10;
            binaryNumber = binaryNumber / 10;

            decimalNumber += lastDigit * base;
            base = base * 2;
        }
        return decimalNumber;
    }

    /**
     * This method accepts two binary numbers and returns sum of input numbers.
     * Example:- firstNum: 101, secondNum: 100, output: 1001
     *
     * @param firstNum
     * @param secondNum
     * @return addition of input numbers
     */
    public Integer addBinaryNumber(Integer firstNum, Integer secondNum) {

        StringBuilder output = new StringBuilder();

        int carry = 0;
        int temp;

        while (firstNum != 0 || secondNum != 0) {

            temp = (firstNum % 10 + secondNum % 10 + carry) % 2;
            output.append(temp);

            carry = (firstNum % 10 + secondNum % 10 + carry) / 2;

            firstNum = firstNum / 10;
            secondNum = secondNum / 10;
        }

        if (carry != 0) {
            output.append(carry);
        }

        return Integer.valueOf(output.reverse()
            .toString());
    }

    /**
    * This method takes two binary number as input and subtract second number from the first number.
    * example:- firstNum: 1000, secondNum: 11, output: 101
    * @param firstNum
    * @param secondNum
    * @return Result of subtraction of secondNum from first
    */
    public Integer substractBinaryNumber(Integer firstNum, Integer secondNum) {

        int onesComplement = Integer.valueOf(getOnesComplement(secondNum));
        StringBuilder output = new StringBuilder();
        int carry = 0;
        int temp;

        while (firstNum != 0 || onesComplement != 0) {

            temp = (firstNum % 10 + onesComplement % 10 + carry) % 2;
            output.append(temp);

            carry = (firstNum % 10 + onesComplement % 10 + carry) / 2;

            firstNum = firstNum / 10;
            onesComplement = onesComplement / 10;
        }

        String additionOfFirstNumAndOnesComplement = output.reverse()
            .toString();

        if (carry == 1) {
            return addBinaryNumber(Integer.valueOf(additionOfFirstNumAndOnesComplement), carry);
        } else {
            return getOnesComplement(Integer.valueOf(additionOfFirstNumAndOnesComplement));
        }
    }

    public Integer getOnesComplement(Integer num) {

        StringBuilder onesComplement = new StringBuilder();
        while (num > 0) {
            int lastDigit = num % 10;
            if (lastDigit == 0) {
                onesComplement.append(1);
            } else {
                onesComplement.append(0);
            }
            num = num / 10;
        }
        return Integer.valueOf(onesComplement.reverse()
            .toString());
    }

}