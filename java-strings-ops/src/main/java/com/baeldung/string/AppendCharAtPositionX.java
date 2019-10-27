/**
 * 
 */
package com.baeldung.string;

/**
 * @author swpraman
 *
 */
public class AppendCharAtPositionX {

    public String addCharUsingCharArray(String str, char ch, int position) {
        validate(str, position);
        int len = str.length();
        char[] updatedArr = new char[len + 1];
        str.getChars(0, position, updatedArr, 0);
        updatedArr[position] = ch;
        str.getChars(position, len, updatedArr, position + 1);
        return new String(updatedArr);
    }

    public String addCharUsingSubstring(String str, char ch, int position) {
        validate(str, position);
        return str.substring(0, position) + ch + str.substring(position);
    }

    public String addCharUsingStringBuilder(String str, char ch, int position) {
        validate(str, position);
        StringBuilder sb = new StringBuilder(str);
        sb.insert(position, ch);
        return sb.toString();
    }

    private void validate(String str, int position) {
        if (str == null) {
            throw new IllegalArgumentException("Str should not be null");
        }
        int len = str.length();
        if (position < 0 || position > len) {
            throw new IllegalArgumentException("position[" + position + "] should be " + "in the range 0.." + len + " for string " + str);
        }
    }

}
