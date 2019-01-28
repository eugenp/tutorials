package com.baeldung.hexToAscii;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 十六进制转Ascii
 */
public class HexToAsciiUnitTest {

    @Test
    public void whenHexToAscii() {
        String asciiString = "http://www.baeldung.com/jackson-serialize-dates";
        String hexEquivalent = "687474703a2f2f7777772e6261656c64756e672e636f6d2f6a61636b736f6e2d73657269616c697a652d6461746573";
        assertEquals(asciiString, hexToAscii(hexEquivalent));
    }

    @Test
    public void whenAsciiToHex() {
        String asciiString = "http://www.baeldung.com/jackson-serialize-dates";
        String hexEquivalent = "687474703a2f2f7777772e6261656c64756e672e636f6d2f6a61636b736f6e2d73657269616c697a652d6461746573";
        System.out.println("asciiToHex(asciiString):{}" + asciiToHex(asciiString));

        assertEquals(hexEquivalent, asciiToHex(asciiString));
    }

    /**
     * 字符串转16进制
     * @param asciiStr
     * @return
     */
    private static String asciiToHex(String asciiStr) {
        char[] chars = asciiStr.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char ch : chars) {
            hex.append(Integer.toHexString((int) ch));
        }
        return hex.toString();
    }

    /**
     * 16进制转字符串
     * @param hexStr
     * @return
     */
    private static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            System.out.println("str:{}" + str);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

}
