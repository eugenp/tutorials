package com.baeldung;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 测试：字符数组转化为string
 * 有3种常用的方法：
 * （1）使用构造函数，例如：new String(char value[], int offset, int count)
 * （2）使用copyValueOf函数，java.lang.String#copyValueOf(char[], int, int)
 * （3）使用valueOf函数，java.lang.String#valueOf(char data[])
 */
public class CharArrayToStringUnitTest {

    @Test
    public void givenCharArray_whenCallingStringConstructor_shouldConvertToString() {
        char[] charArray = { 'c', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r' };
        String result = new String(charArray);
        String expectedValue = "character";

        assertEquals(expectedValue, result);
    }
    /**
     * @see  {@link java.lang.String new String(char value[], int offset, int count)}
     * 输入：字符串数组
     * 输出：指定长度的字符串
     */
    @Test
    public void givenCharArray_whenCallingStringConstructorWithOffsetAndLength_shouldConvertToString() {
        char[] charArray = { 'c', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r' };
        String result = new String(charArray, 4, 3);
        String expectedValue = "act";

        assertEquals(expectedValue, result);
    }

    @Test
    public void givenCharArray_whenCallingStringCopyValueOf_shouldConvertToString() {
        char[] charArray = { 'c', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r' };
        String result = String.copyValueOf(charArray);
        String expectedValue = "character";

        assertEquals(expectedValue, result);
    }

    /**
     * @see java.lang.String#copyValueOf(char[], int, int)
     * 输入：字符串数组
     * 输出：指定长度的字符串
     */
    @Test
    public void givenCharArray_whenCallingStringCopyValueOfWithOffsetAndLength_shouldConvertToString() {
        char[] charArray = { 'c', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r' };
        String result = String.copyValueOf(charArray, 0, 4);
        String expectedValue = "char";

        assertEquals(expectedValue, result);
    }

    /**
     * @see java.lang.String#valueOf(char data[])
     * 输入：字符串数组
     * 输出：指定长度的字符串
     */
    @Test
    public void givenCharArray_whenCallingStringValueOf_shouldConvertToString() {
        char[] charArray = { 'c', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r' };
        String result = String.valueOf(charArray);
        String expectedValue = "character";

        assertEquals(expectedValue, result);
    }

    @Test
    public void givenCharArray_whenCallingStringValueOfWithOffsetAndLength_shouldConvertToString() {
        char[] charArray = { 'c', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r' };
        String result = String.valueOf(charArray, 3, 4);
        String expectedValue = "ract";

        assertEquals(expectedValue, result);
    }
}
