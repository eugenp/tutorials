package com.baeldung;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试：字符转化为string
 * 有3种常用的方法：
 * （1）使用valueOf函数，例如：java.lang.String#valueOf(char)
 * （2）使用Character#toString(char)函数，例如：java.lang.Character#toString(char)
 * （3）使用String.format("%c", givenChar)函数，例如： String.format("%c", givenChar);
 * （4）使用givenChar + ""
 *
 */
public class CharToStringUnitTest {

    /**
     * @see java.lang.String#valueOf(char)
     * 输入：字符
     * 输出：指定长度的字符串
     */
    @Test
    public void givenChar_whenCallingStringValueOf_shouldConvertToString() {
        final char givenChar = 'x';

        final String result = String.valueOf(givenChar);

        assertThat(result).isEqualTo("x");
    }

    /**
     * @see java.lang.Character#toString(char)
     * 输入：字符
     * 输出：指定长度的字符串
     */
    @Test
    public void givenChar_whenCallingToStringOnCharacter_shouldConvertToString() {
        final char givenChar = 'x';

        final String result = Character.toString(givenChar);

        assertThat(result).isEqualTo("x");
    }

    @Test
    public void givenChar_whenCallingCharacterConstructor_shouldConvertToString3() {
        final char givenChar = 'x';

        final String result = new Character(givenChar).toString();

        assertThat(result).isEqualTo("x");
    }

    @Test
    public void givenChar_whenConcatenated_shouldConvertToString4() {
        final char givenChar = 'x';

        final String result = givenChar + "";

        assertThat(result).isEqualTo("x");
    }

    @Test
    public void givenChar_whenFormated_shouldConvertToString5() {
        final char givenChar = 'x';

        final String result = String.format("%c", givenChar);

        assertThat(result).isEqualTo("x");
    }
}
