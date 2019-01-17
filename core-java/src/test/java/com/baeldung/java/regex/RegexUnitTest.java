package com.baeldung.java.regex;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * 测试：正则表达式
 * @see {http://tool.oschina.net/uploads/apidocs/jquery/regexp.html} 查看正则表达式手册。
 */
public class RegexUnitTest {
    private static Pattern pattern;
    private static Matcher matcher;

    @Test
    public void givenText_whenSimpleRegexMatches_thenCorrect() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foo");
        assertTrue(matcher.find());
    }

    @Test
    public void givenText_whenSimpleRegexMatchesTwice_thenCorrect() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foofoo");
        int matches = 0;
        while (matcher.find()){
            matches++;
        }
        assertEquals(matches, 2);

    }

    @Test
    public void givenText_whenMatchesWithDotMetach_thenCorrect() {
        int matches = runTest(".", "foo");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRepeatedText_whenMatchesOnceWithDotMetach_thenCorrect() {
        int matches = runTest("foo.", "foofoo");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenORSet_whenMatchesAny_thenCorrect() {
        int matches = runTest("[abc]", "b");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenORSet_whenMatchesAnyAndAll_thenCorrect() {
        int matches = runTest("[abc]", "cab");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    @Test
    public void givenORSet_whenMatchesAllCombinations_thenCorrect() {
        int matches = runTest("[bcr]at", "bat cat rat");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    @Test
    public void givenNORSet_whenMatchesNon_thenCorrect() {
        int matches = runTest("[^abc]", "g");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenNORSet_whenMatchesAllExceptElements_thenCorrect() {
        int matches = runTest("[^bcr]at", "sat mat eat");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenUpperCaseRange_whenMatchesUpperCase_thenCorrect() {
        int matches = runTest("[A-Z]", "Two Uppercase alphabets 34 overall");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 2);
    }

    @Test
    public void givenLowerCaseRange_whenMatchesLowerCase_thenCorrect() {
        int matches = runTest("[a-z]", "Two Uppercase alphabets 34 overall");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 26);
    }

    @Test
    public void givenBothLowerAndUpperCaseRange_whenMatchesAllLetters_thenCorrect() {
        int matches = runTest("[a-zA-Z]", "Two Uppercase alphabets 34 overall");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 28);
    }

    @Test
    public void givenNumberRange_whenMatchesAccurately_thenCorrect() {
        int matches = runTest("[1-5]", "Two Uppercase alphabets 34 overall");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 2);
    }

    @Test
    public void givenNumberRange_whenMatchesAccurately_thenCorrect2() {
        int matches = runTest("[30-35]", "Two Uppercase alphabets 34 overall");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenTwoSets_whenMatchesUnion_thenCorrect() {
        int matches = runTest("[1-3[7-9]]", "123456789");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 6);
    }

    /**
     *取交集
     */
    @Test
    public void givenTwoSets_whenMatchesIntersection_thenCorrect() {
        int matches = runTest("[1-6&&[3-9]]", "123456789");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 4);
    }

    /**
     * 减法
     */
    @Test
    public void givenSetWithSubtraction_whenMatchesAccurately_thenCorrect() {
        int matches = runTest("[0-9&&[^2468]]", "123456789");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 5);
    }

    @Test
    public void givenDigits_whenMatches_thenCorrect() {
        int matches = runTest("\\d", "123");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    /**
     * 获取大小写字母
     */
    @Test
    public void givenNonDigits_whenMatches_thenCorrect() {
        int matches = runTest("\\D", "a6cDF68");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 4);
    }

    /**
     * 获取空格
     * \s:匹配任何空白字符，包括空格、制表符、换页符等等。等价于[ \f\n\r\t\v]。
     */
    @Test
    public void givenWhiteSpace_whenMatches_thenCorrect() {
        int matches = runTest("\\s", "a  c ");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    /**
     * 获取非空格字符
     * \S:匹配任何非空白字符。等价于[^ \f\n\r\t\v]。
     */
    @Test
    public void givenNonWhiteSpace_whenMatches_thenCorrect() {
        int matches = runTest("\\S", "a c 1 \\n \\t \n");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 7);
    }

    /**
     * \w:匹配包括下划线的任何单词字符。等价于“[A-Za-z0-9_]”。
     */
    @Test
    public void givenWordCharacter_whenMatches_thenCorrect() {
        int matches = runTest("\\w", "hi!");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 2);
    }

    /**
     * \W:匹配任何非单词字符。等价于“[^A-Za-z0-9_]”。
     */
    @Test
    public void givenNonWordCharacter_whenMatches_thenCorrect() {
        int matches = runTest("\\W", "hi!");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    /**
     * ?：匹配前面的子表达式零次或一次。例如，“do(es)?”可以匹配“does”或“does”中的“do”。?等价于{0,1}。
     */
    @Test
    public void givenZeroOrOneQuantifier_whenMatches_thenCorrect() {
        int matches = runTest("\\a?", "hi");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    /**
     * 匹配0或1次
     */
    @Test
    public void givenZeroOrOneQuantifier_whenMatches_thenCorrect2() {
        int matches = runTest("\\a{0,1}", "hi");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    /**
     * *:匹配前面的子表达式零次或多次。例如，zo*能匹配“z”以及“zoo”。*等价于{0,}。
     */
    @Test
    public void givenZeroOrManyQuantifier_whenMatches_thenCorrect() {
        int matches = runTest("\\a*", "hi");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    /**
     * 匹配0或多次
     */
    @Test
    public void givenZeroOrManyQuantifier_whenMatches_thenCorrect2() {
        int matches = runTest("\\a{0,}", "hi");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    /**
     * +:匹配前面的子表达式一次或多次。例如，“zo+”能匹配“zo”以及“zoo”，但不能匹配“z”。+等价于{1,}。
     */
    @Test
    public void givenOneOrManyQuantifier_whenMatches_thenCorrect() {
        int matches = runTest("\\a+", "hi");
        System.out.println("matches:{}" + matches);
        assertFalse(matches > 0);
    }

    /**
     * 匹配0或多次
     */
    @Test
    public void givenOneOrManyQuantifier_whenMatches_thenCorrect2() {
        int matches = runTest("\\a{1,}", "hi");
        System.out.println("matches:{}" + matches);
        assertFalse(matches > 0);
    }

    /**
     * {n}:n是一个非负整数。匹配确定的n次。例如，“o{2}”不能匹配“Bob”中的“o”，但是能匹配“food”中的两个o。
     */
    @Test
    public void givenBraceQuantifier_whenMatches_thenCorrect() {
        int matches = runTest("a{3}", "aaaaaaaaa");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    @Test
    public void givenBraceQuantifier_whenFailsToMatch_thenCorrect() {
        int matches = runTest("a{3}", "aa");
        System.out.println("matches:{}" + matches);
        assertFalse(matches > 0);
    }

    /**
     *{n,m}:m和n均为非负整数，其中n<=m。最少匹配n次且最多匹配m次。例如，“o{1,3}”将匹配“fooooood”中的前三个o。“o{0,1}”等价于“o?”。请注意在逗号和两个数之间不能有空格。
     */
    @Test
    public void givenBraceQuantifierWithRange_whenMatches_thenCorrect() {
        int matches = runTest("a{2,3}", "aaaa");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenBraceQuantifierWithRange_whenMatchesLazily_thenCorrect() {
        int matches = runTest("a{2,3}?", "aaaa");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 2);
    }

    @Test
    public void givenCapturingGroup_whenMatches_thenCorrect() {
        int matches = runTest("(\\d\\d)", "12");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenCapturingGroup_whenMatches_thenCorrect2() {
        int matches = runTest("(\\d\\d)", "1212");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 2);
    }

    @Test
    public void givenCapturingGroup_whenMatches_thenCorrect3() {
        int matches = runTest("(\\d\\d)(\\d\\d)", "1212");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenCapturingGroup_whenMatchesWithBackReference_thenCorrect() {
        int matches = runTest("(\\d\\d)\\1", "1212");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenCapturingGroup_whenMatchesWithBackReference_thenCorrect2() {
        int matches = runTest("(\\d\\d)\\1\\1\\1", "12121212");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenCapturingGroupAndWrongInput_whenMatchFailsWithBackReference_thenCorrect() {
        int matches = runTest("(\\d\\d)\\1", "1213");
        System.out.println("matches:{}" + matches);
        assertFalse(matches > 0);
    }

    @Test
    public void givenText_whenMatchesAtBeginning_thenCorrect() {
        int matches = runTest("^dog", "dogs are friendly");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenTextAndWrongInput_whenMatchFailsAtBeginning_thenCorrect() {
        int matches = runTest("^dog", "are dogs are friendly?");
        System.out.println("matches:{}" + matches);
        assertFalse(matches > 0);
    }

    @Test
    public void givenText_whenMatchesAtEnd_thenCorrect() {
        int matches = runTest("dog$", "Man's best friend is a dog");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenTextAndWrongInput_whenMatchFailsAtEnd_thenCorrect() {
        int matches = runTest("dog$", "is a dog man's best friend?");
        System.out.println("matches:{}" + matches);
        assertFalse(matches > 0);
    }

    /**
     * \b:匹配一个单词边界，也就是指单词和空格间的位置。例如，“er\b”可以匹配“never”中的“er”，但不能匹配“verb”中的“er”。
     */
    @Test
    public void givenText_whenMatchesAtWordBoundary_thenCorrect() {
        int matches = runTest("\\bdog\\b", "a dog is friendly");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenText_whenMatchesAtWordBoundary_thenCorrect2() {
        int matches = runTest("\\bdog\\b", "dog is man's best friend");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenWrongText_whenMatchFailsAtWordBoundary_thenCorrect() {
        int matches = runTest("\\bdog\\b", "snoop dogg is a rapper");
        System.out.println("matches:{}" + matches);
        assertFalse(matches > 0);
    }

    /**
     * \B:匹配非单词边界。“er\B”能匹配“verb”中的“er”，但不能匹配“never”中的“er”。
     */
    @Test
    public void givenText_whenMatchesAtWordAndNonBoundary_thenCorrect() {
        int matches = runTest("\\bdog\\B", "snoop dogg is a rapper");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegexWithoutCanonEq_whenMatchFailsOnEquivalentUnicode_thenCorrect() {
        int matches = runTest("\u00E9", "\u0065\u0301");
        System.out.println("matches:{}" + matches);
        assertFalse(matches > 0);
    }

    @Test
    public void givenRegexWithCanonEq_whenMatchesOnEquivalentUnicode_thenCorrect() {
        int matches = runTest("\u00E9", "\u0065\u0301", Pattern.CANON_EQ);
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegexWithDefaultMatcher_whenMatchFailsOnDifferentCases_thenCorrect() {
        int matches = runTest("dog", "This is a Dog");
        System.out.println("matches:{}" + matches);
        assertFalse(matches > 0);
    }

    @Test
    public void givenRegexWithCaseInsensitiveMatcher_whenMatchesOnDifferentCases_thenCorrect() {
        int matches = runTest("dog", "This is a Dog", Pattern.CASE_INSENSITIVE);
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegexWithEmbeddedCaseInsensitiveMatcher_whenMatchesOnDifferentCases_thenCorrect() {
        int matches = runTest("(?i)dog", "This is a Dog");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegexWithComments_whenMatchFailsWithoutFlag_thenCorrect() {
        int matches = runTest("dog$  #check for word dog at end of text", "This is a dog");
        System.out.println("matches:{}" + matches);
        assertFalse(matches > 0);
    }

    @Test
    public void givenRegexWithComments_whenMatchesWithFlag_thenCorrect() {
        int matches = runTest("dog$  #check for word dog at end of text", "This is a dog", Pattern.COMMENTS);
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegexWithComments_whenMatchesWithEmbeddedFlag_thenCorrect() {
        int matches = runTest("(?x)dog$  #check for word dog at end of text", "This is a dog");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegexWithLineTerminator_whenMatchFails_thenCorrect() {
        Pattern pattern = Pattern.compile("(.*)");
        Matcher matcher = pattern.matcher("this is a text" + System.getProperty("line.separator") + " continued on another line");
        matcher.find();
        assertEquals("this is a text", matcher.group(1));
    }

    @Test
    public void givenRegexWithLineTerminator_whenMatchesWithDotall_thenCorrect() {
        Pattern pattern = Pattern.compile("(.*)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher("this is a text" + System.getProperty("line.separator") + " continued on another line");
        matcher.find();
        assertEquals("this is a text" + System.getProperty("line.separator") + " continued on another line", matcher.group(1));
    }

    @Test
    public void givenRegexWithLineTerminator_whenMatchesWithEmbeddedDotall_thenCorrect() {
        Pattern pattern = Pattern.compile("(?s)(.*)");
        Matcher matcher = pattern.matcher("this is a text" + System.getProperty("line.separator") + " continued on another line");
        matcher.find();
        assertEquals("this is a text" + System.getProperty("line.separator") + " continued on another line", matcher.group(1));
    }

    @Test
    public void givenRegex_whenMatchesWithoutLiteralFlag_thenCorrect() {
        int matches = runTest("(.*)", "text");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegex_whenMatchFailsWithLiteralFlag_thenCorrect() {
        int matches = runTest("(.*)", "text", Pattern.LITERAL);
        System.out.println("matches:{}" + matches);
        assertFalse(matches > 0);
    }

    @Test
    public void givenRegex_whenMatchesWithLiteralFlag_thenCorrect() {
        int matches = runTest("(.*)", "text(.*)", Pattern.LITERAL);
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegex_whenMatchFailsWithoutMultilineFlag_thenCorrect() {
        int matches = runTest("dog$", "This is a dog" + System.getProperty("line.separator") + "this is a fox");
        System.out.println("matches:{}" + matches);
        assertFalse(matches > 0);
    }

    @Test
    public void givenRegex_whenMatchesWithMultilineFlag_thenCorrect() {
        int matches = runTest("dog$", "This is a dog" + System.getProperty("line.separator") + "this is a fox", Pattern.MULTILINE);
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegex_whenMatchesWithEmbeddedMultilineFlag_thenCorrect() {
        int matches = runTest("(?m)dog$", "This is a dog" + System.getProperty("line.separator") + "this is a fox");
        System.out.println("matches:{}" + matches);
        assertTrue(matches > 0);
    }

    @Test
    public void givenMatch_whenGetsIndices_thenCorrect() {
        Pattern pattern = Pattern.compile("dog");
        Matcher matcher = pattern.matcher("This dog is mine");
        matcher.find();
        assertEquals(5, matcher.start());
        assertEquals(8, matcher.end());
    }

    @Test
    public void whenStudyMethodsWork_thenCorrect() {
        Pattern pattern = Pattern.compile("dog");
        Matcher matcher = pattern.matcher("dogs are friendly");
        assertTrue(matcher.lookingAt());
        assertFalse(matcher.matches());

    }

    @Test
    public void whenMatchesStudyMethodWorks_thenCorrect() {
        Pattern pattern = Pattern.compile("dog");
        Matcher matcher = pattern.matcher("dog");
        assertTrue(matcher.matches());

    }

    @Test
    public void whenReplaceFirstWorks_thenCorrect() {
        Pattern pattern = Pattern.compile("dog");
        Matcher matcher = pattern.matcher("dogs are domestic animals, dogs are friendly");
        String newStr = matcher.replaceFirst("cat");
        assertEquals("cats are domestic animals, dogs are friendly", newStr);

    }

    @Test
    public void whenReplaceAllWorks_thenCorrect() {
        Pattern pattern = Pattern.compile("dog");
        Matcher matcher = pattern.matcher("dogs are domestic animals, dogs are friendly");
        String newStr = matcher.replaceAll("cat");
        assertEquals("cats are domestic animals, cats are friendly", newStr);

    }

    public synchronized static int runTest(String regex, String text) {
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(text);
        int matches = 0;
        while (matcher.find()){
            matches++;
        }
        return matches;
    }

    public synchronized static int runTest(String regex, String text, int flags) {
        pattern = Pattern.compile(regex, flags);
        matcher = pattern.matcher(text);
        int matches = 0;
        while (matcher.find()){
            matches++;
        }
        return matches;
    }
}
