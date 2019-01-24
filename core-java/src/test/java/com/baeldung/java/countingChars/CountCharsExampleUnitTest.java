package com.baeldung.java.countingChars;

import com.google.common.base.CharMatcher;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.function.IntPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;


/***
 * Example of counting chars in a String.
 * 测试：在字符串中统计某个字符出现的次数。
 */
public class CountCharsExampleUnitTest {

    /**
     * 直接for循环的方式
     */
    @Test
    public void givenString_whenUsingLoop_thenCountChars() {
        String someString = "elephant";
        char someChar = 'e';
        int count = 0;
        for (int i = 0; i < someString.length(); i++) {
            if (someString.charAt(i) == someChar) {
                count++;
            }
        }
        assertEquals(2, count);
    }

    /**
     * length = 原串的长度 - 原串被replace后生成的新串的长度
     */
    @Test
    public void givenString_whenUsingReplace_thenCountChars() {
        String someString = "elephant";
        int count = someString.length() - someString.replace("e", "").length();
        assertEquals(2, count);
    }

    /**
     * 使用split
     */
    @Test
    public void givenString_whenUsingSplit_thenCountChars() {
        String someString = "elephant";

        int count = someString.split("e", -1).length - 1;
        assertEquals(2, count);
    }

    /**
     * 使用正则表达式
     */
    @Test
    public void givenString_whenUsingReqExp_thenCountChars() {
        Pattern pattern = Pattern.compile("[^e]*e");
        Matcher matcher = pattern.matcher("elephant");
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        assertEquals(2, count);
    }

    /**
     * 使用递归算法（递归往后找）
     */
    @Test
    public void givenString_whenUsingRecursion_thenCountChars() {
        int count = useRecursion("elephant", 'e', 0);
        assertEquals(2, count);
    }

    private int useRecursion(String someString, char searchedChar, int index) {
        if (index >= someString.length()) {
            return 0;
        }

        int count = someString.charAt(index) == searchedChar ? 1 : 0;
        return count + useRecursion(someString, searchedChar, index + 1);
    }

    /**
     * 使用{@link org.apache.commons.lang.StringUtils#countMatches(String, String)}}
     * @throws InterruptedException
     */
    @Test
    public void givenString_whenUsingStringUtils_thenCountChars() throws InterruptedException {
        int count = StringUtils.countMatches("elephant", "e");
        assertEquals(2, count);
    }

    /**
     * (1)使用{@link java.lang.CharSequence#chars()} 和 {@link  java.util.stream.IntStream#filter(IntPredicate)}
     * (2)使用{@link java.lang.CharSequence#codePoints()} 和 {@link  java.util.stream.IntStream#filter(IntPredicate)}
     */
    @Test
    public void givenString_whenUsingJava8Features_thenCountChars() {
        String someString = "elephant";
        long count = someString.chars().filter(new IntPredicate() {
            @Override
            public boolean test(int ch) {
                return ch == 'e';
            }
        }).count();

        assertEquals(2, count);

        long count2 = someString.codePoints().filter(new IntPredicate() {
            @Override
            public boolean test(int ch) {
                return ch == 'e';
            }
        }).count();
        assertEquals(2, count2);
    }

    /**
     *使用guava中的{@link com.google.common.base.CharMatcher#is(char)} 和 {@link com.google.common.base.CharMatcher#countIn(CharSequence)}
     */
    @Test
    public void givenString_whenUsingGuavaCharMatcher_thenCountChars() {
        int count = CharMatcher.is('e').countIn("elephant");
        assertEquals(2, count);
    }

}
