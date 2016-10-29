package com.baeldung.hexToAscii;

public class HexToAscii {

    public static void main(String[] args) {

        String demoString = "http://www.baeldung.com/jackson-serialize-dates";

        System.out.println("Original statement: " + demoString);

        String hexEquivalent = asciiToHex(demoString);
        System.out.println("Hex equivalent: " + hexEquivalent);

        String asciiEquivalent = hexToASCII(hexEquivalent);
        System.out.println("Ascii equivalent: " + asciiEquivalent);
    }

    private static String asciiToHex(String asciiStr) {

        char[] chars = asciiStr.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char ch : chars) {
            hex.append(Integer.toHexString((int) ch));
        }

        return hex.toString();
    }

    private static String hexToASCII(String hexStr) {

        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }
}
