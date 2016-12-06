package com.baeldung.java.regex;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest {
    private static Pattern pattern;
    private static Matcher matcher;

    @Test
    public void givenText_whenSimpleRegexMatches_thenCorrect() {
        Result result = runTest("foo", "foo");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 1);

    }

    @Test
    public void givenText_whenSimpleRegexMatchesTwice_thenCorrect() {
        Result result = runTest("foo", "foofoo");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 2);

    }

    @Test
    public void givenText_whenMatchesWithDotMetach_thenCorrect() {
        Result result = runTest(".", "foo");
        assertTrue(result.isFound());
    }

    @Test
    public void givenRepeatedText_whenMatchesOnceWithDotMetach_thenCorrect() {
        Result result = runTest("foo.", "foofoo");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 1);
    }

    @Test
    public void givenORSet_whenMatchesAny_thenCorrect() {
        Result result = runTest("[abc]", "b");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 1);
    }

    @Test
    public void givenORSet_whenMatchesAnyAndAll_thenCorrect() {
        Result result = runTest("[abc]", "cab");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 3);
    }

    @Test
    public void givenORSet_whenMatchesAllCombinations_thenCorrect() {
        Result result = runTest("[bcr]at", "bat cat rat");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 3);
    }

    @Test
    public void givenNORSet_whenMatchesNon_thenCorrect() {
        Result result = runTest("[^abc]", "g");
        assertTrue(result.isFound());
    }

    @Test
    public void givenNORSet_whenMatchesAllExceptElements_thenCorrect() {
        Result result = runTest("[^bcr]at", "sat mat eat");
        assertTrue(result.isFound());
    }

    @Test
    public void givenUpperCaseRange_whenMatchesUpperCase_thenCorrect() {
        Result result = runTest("[A-Z]", "Two Uppercase alphabets 34 overall");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 2);
    }

    @Test
    public void givenLowerCaseRange_whenMatchesLowerCase_thenCorrect() {
        Result result = runTest("[a-z]", "Two Uppercase alphabets 34 overall");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 26);
    }

    @Test
    public void givenBothLowerAndUpperCaseRange_whenMatchesAllLetters_thenCorrect() {
        Result result = runTest("[a-zA-Z]", "Two Uppercase alphabets 34 overall");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 28);
    }

    @Test
    public void givenNumberRange_whenMatchesAccurately_thenCorrect() {
        Result result = runTest("[1-5]", "Two Uppercase alphabets 34 overall");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 2);
    }

    @Test
    public void givenNumberRange_whenMatchesAccurately_thenCorrect2() {
        Result result = runTest("[30-35]", "Two Uppercase alphabets 34 overall");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 1);
    }

    @Test
    public void givenTwoSets_whenMatchesUnion_thenCorrect() {
        Result result = runTest("[1-3[7-9]]", "123456789");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 6);
    }

    @Test
    public void givenTwoSets_whenMatchesIntersection_thenCorrect() {
        Result result = runTest("[1-6&&[3-9]]", "123456789");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 4);
    }

    @Test
    public void givenSetWithSubtraction_whenMatchesAccurately_thenCorrect() {
        Result result = runTest("[0-9&&[^2468]]", "123456789");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 5);
    }

    @Test
    public void givenDigits_whenMatches_thenCorrect() {
        Result result = runTest("\\d", "123");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 3);
    }

    @Test
    public void givenNonDigits_whenMatches_thenCorrect() {
        Result result = runTest("\\D", "a6c");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 2);
    }

    @Test
    public void givenWhiteSpace_whenMatches_thenCorrect() {
        Result result = runTest("\\s", "a c");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 1);
    }

    @Test
    public void givenNonWhiteSpace_whenMatches_thenCorrect() {
        Result result = runTest("\\S", "a c");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 2);
    }

    @Test
    public void givenWordCharacter_whenMatches_thenCorrect() {
        Result result = runTest("\\w", "hi!");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 2);
    }

    @Test
    public void givenNonWordCharacter_whenMatches_thenCorrect() {
        Result result = runTest("\\W", "hi!");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 1);
    }

    @Test
    public void givenZeroOrOneQuantifier_whenMatches_thenCorrect() {
        Result result = runTest("\\a?", "hi");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 3);
    }

    @Test
    public void givenZeroOrOneQuantifier_whenMatches_thenCorrect2() {
        Result result = runTest("\\a{0,1}", "hi");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 3);
    }

    @Test
    public void givenZeroOrManyQuantifier_whenMatches_thenCorrect() {
        Result result = runTest("\\a*", "hi");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 3);
    }

    @Test
    public void givenZeroOrManyQuantifier_whenMatches_thenCorrect2() {
        Result result = runTest("\\a{0,}", "hi");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 3);
    }

    @Test
    public void givenOneOrManyQuantifier_whenMatches_thenCorrect() {
        Result result = runTest("\\a+", "hi");
        assertFalse(result.isFound());
    }

    @Test
    public void givenOneOrManyQuantifier_whenMatches_thenCorrect2() {
        Result result = runTest("\\a{1,}", "hi");
        assertFalse(result.isFound());
    }

    @Test
    public void givenBraceQuantifier_whenMatches_thenCorrect() {
        Result result = runTest("a{3}", "aaaaaa");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 2);
    }

    @Test
    public void givenBraceQuantifier_whenFailsToMatch_thenCorrect() {
        Result result = runTest("a{3}", "aa");
        assertFalse(result.isFound());
    }

    @Test
    public void givenBraceQuantifierWithRange_whenMatches_thenCorrect() {
        Result result = runTest("a{2,3}", "aaaa");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 1);
    }

    @Test
    public void givenBraceQuantifierWithRange_whenMatchesLazily_thenCorrect() {
        Result result = runTest("a{2,3}?", "aaaa");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 2);
    }

    @Test
    public void givenCapturingGroup_whenMatches_thenCorrect() {
        Result result = runTest("(\\d\\d)", "12");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 1);
    }

    @Test
    public void givenCapturingGroup_whenMatches_thenCorrect2() {
        Result result = runTest("(\\d\\d)", "1212");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 2);
    }

    @Test
    public void givenCapturingGroup_whenMatches_thenCorrect3() {
        Result result = runTest("(\\d\\d)(\\d\\d)", "1212");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 1);
    }

    @Test
    public void givenCapturingGroup_whenMatchesWithBackReference_thenCorrect() {
        Result result = runTest("(\\d\\d)\\1", "1212");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 1);
    }

    @Test
    public void givenCapturingGroup_whenMatchesWithBackReference_thenCorrect2() {
        Result result = runTest("(\\d\\d)\\1\\1\\1", "12121212");
        assertTrue(result.isFound());
        assertEquals(result.getCount(), 1);
    }

    @Test
    public void givenCapturingGroupAndWrongInput_whenMatchFailsWithBackReference_thenCorrect() {
        Result result = runTest("(\\d\\d)\\1", "1213");
        assertFalse(result.isFound());
    }

    @Test
    public void givenText_whenMatchesAtBeginning_thenCorrect() {
        Result result = runTest("^dog", "dogs are friendly");
        assertTrue(result.isFound());
    }

    @Test
    public void givenTextAndWrongInput_whenMatchFailsAtBeginning_thenCorrect() {
        Result result = runTest("^dog", "are dogs are friendly?");
        assertFalse(result.isFound());
    }

    @Test
    public void givenText_whenMatchesAtEnd_thenCorrect() {
        Result result = runTest("dog$", "Man's best friend is a dog");
        assertTrue(result.isFound());
    }

    @Test
    public void givenTextAndWrongInput_whenMatchFailsAtEnd_thenCorrect() {
        Result result = runTest("dog$", "is a dog man's best friend?");
        assertFalse(result.isFound());
    }

    @Test
    public void givenText_whenMatchesAtWordBoundary_thenCorrect() {
        Result result = runTest("\\bdog\\b", "a dog is friendly");
        assertTrue(result.isFound());
    }

    @Test
    public void givenText_whenMatchesAtWordBoundary_thenCorrect2() {
        Result result = runTest("\\bdog\\b", "dog is man's best friend");
        assertTrue(result.isFound());
    }

    @Test
    public void givenWrongText_whenMatchFailsAtWordBoundary_thenCorrect() {
        Result result = runTest("\\bdog\\b", "snoop dogg is a rapper");
        assertFalse(result.isFound());
    }

    @Test
    public void givenText_whenMatchesAtWordAndNonBoundary_thenCorrect() {
        Result result = runTest("\\bdog\\B", "snoop dogg is a rapper");
        assertTrue(result.isFound());
    }

    @Test
    public void givenRegexWithoutCanonEq_whenMatchFailsOnEquivalentUnicode_thenCorrect() {
        Result result = runTest("\u00E9", "\u0065\u0301");
        assertFalse(result.isFound());
    }

    @Test
    public void givenRegexWithCanonEq_whenMatchesOnEquivalentUnicode_thenCorrect() {
        Result result = runTest("\u00E9", "\u0065\u0301", Pattern.CANON_EQ);
        assertTrue(result.isFound());
    }

    @Test
    public void givenRegexWithDefaultMatcher_whenMatchFailsOnDifferentCases_thenCorrect() {
        Result result = runTest("dog", "This is a Dog");
        assertFalse(result.isFound());
    }

    @Test
    public void givenRegexWithCaseInsensitiveMatcher_whenMatchesOnDifferentCases_thenCorrect() {
        Result result = runTest("dog", "This is a Dog", Pattern.CASE_INSENSITIVE);
        assertTrue(result.isFound());
    }

    @Test
    public void givenRegexWithEmbeddedCaseInsensitiveMatcher_whenMatchesOnDifferentCases_thenCorrect() {
        Result result = runTest("(?i)dog", "This is a Dog");
        assertTrue(result.isFound());
    }

    @Test
    public void givenRegexWithComments_whenMatchFailsWithoutFlag_thenCorrect() {
        Result result = runTest("dog$  #check for word dog at end of text", "This is a dog");
        assertFalse(result.isFound());
    }

    @Test
    public void givenRegexWithComments_whenMatchesWithFlag_thenCorrect() {
        Result result = runTest("dog$  #check for word dog at end of text", "This is a dog", Pattern.COMMENTS);
        assertTrue(result.isFound());
    }

    @Test
    public void givenRegexWithComments_whenMatchesWithEmbeddedFlag_thenCorrect() {
        Result result = runTest("(?x)dog$  #check for word dog at end of text", "This is a dog");
        assertTrue(result.isFound());
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
        Result result = runTest("(.*)", "text");
        assertTrue(result.isFound());
    }

    @Test
    public void givenRegex_whenMatchFailsWithLiteralFlag_thenCorrect() {
        Result result = runTest("(.*)", "text", Pattern.LITERAL);
        assertFalse(result.isFound());
    }

    @Test
    public void givenRegex_whenMatchesWithLiteralFlag_thenCorrect() {
        Result result = runTest("(.*)", "text(.*)", Pattern.LITERAL);
        assertTrue(result.isFound());
    }

    @Test
    public void givenRegex_whenMatchFailsWithoutMultilineFlag_thenCorrect() {
        Result result = runTest("dog$", "This is a dog" + System.getProperty("line.separator") + "this is a fox");
        assertFalse(result.isFound());
    }

    @Test
    public void givenRegex_whenMatchesWithMultilineFlag_thenCorrect() {
        Result result = runTest("dog$", "This is a dog" + System.getProperty("line.separator") + "this is a fox", Pattern.MULTILINE);
        assertTrue(result.isFound());
    }

    @Test
    public void givenRegex_whenMatchesWithEmbeddedMultilineFlag_thenCorrect() {
        Result result = runTest("(?m)dog$", "This is a dog" + System.getProperty("line.separator") + "this is a fox");
        assertTrue(result.isFound());
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

    public synchronized static Result runTest(String regex, String text) {
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(text);
        Result result = new Result();
        while (matcher.find())
            result.setCount(result.getCount() + 1);
        if (result.getCount() > 0)
            result.setFound(true);
        return result;
    }

    public synchronized static Result runTest(String regex, String text, int flags) {
        pattern = Pattern.compile(regex, flags);
        matcher = pattern.matcher(text);
        Result result = new Result();
        while (matcher.find())
            result.setCount(result.getCount() + 1);
        if (result.getCount() > 0)
            result.setFound(true);
        return result;
    }
}
