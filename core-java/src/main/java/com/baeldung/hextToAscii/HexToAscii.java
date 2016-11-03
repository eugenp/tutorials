package com.baeldung.hexToAscii;

public class HexToAscii {

    @Test
    public static void main() {

        String asciiString = "http://www.baeldung.com/jackson-serialize-dates";
        String hexEquivalent = "687474703a2f2f7777772e6261656c64756e672e636f6d2f6a61636b736f6e2d73657269616c697a652d6461746573";

        assertEquals(hexEquivalent, asciiToHex(asciiString));
        assertEquals(asciiString, hexToAscii(hexEquivalent));
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
