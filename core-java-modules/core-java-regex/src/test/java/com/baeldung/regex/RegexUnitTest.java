package com.baeldung.regex;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

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
        while (matcher.find())
            matches++;
        assertEquals(matches, 2);

    }

    @Test
    public void givenText_whenMatchesWithDotMetach_thenCorrect() {
        int matches = runTest(".", "foo");
        assertTrue(matches > 0);
    }

    @Test
    public void givenRepeatedText_whenMatchesOnceWithDotMetach_thenCorrect() {
        int matches = runTest("foo.", "foofoo");
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenORSet_whenMatchesAny_thenCorrect() {
        int matches = runTest("[abc]", "b");
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenORSet_whenMatchesAnyAndAll_thenCorrect() {
        int matches = runTest("[abc]", "cab");
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    @Test
    public void givenORSet_whenMatchesAllCombinations_thenCorrect() {
        int matches = runTest("[bcr]at", "bat cat rat");
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    @Test
    public void givenNORSet_whenMatchesNon_thenCorrect() {
        int matches = runTest("[^abc]", "g");
        assertTrue(matches > 0);
    }

    @Test
    public void givenNORSet_whenMatchesAllExceptElements_thenCorrect() {
        int matches = runTest("[^bcr]at", "sat mat eat");
        assertTrue(matches > 0);
    }

    @Test
    public void givenUpperCaseRange_whenMatchesUpperCase_thenCorrect() {
        int matches = runTest("[A-Z]", "Two Uppercase alphabets 34 overall");
        assertTrue(matches > 0);
        assertEquals(matches, 2);
    }

    @Test
    public void givenLowerCaseRange_whenMatchesLowerCase_thenCorrect() {
        int matches = runTest("[a-z]", "Two Uppercase alphabets 34 overall");
        assertTrue(matches > 0);
        assertEquals(matches, 26);
    }

    @Test
    public void givenBothLowerAndUpperCaseRange_whenMatchesAllLetters_thenCorrect() {
        int matches = runTest("[a-zA-Z]", "Two Uppercase alphabets 34 overall");
        assertTrue(matches > 0);
        assertEquals(matches, 28);
    }

    @Test
    public void givenNumberRange_whenMatchesAccurately_thenCorrect() {
        int matches = runTest("[1-5]", "Two Uppercase alphabets 34 overall");
        assertTrue(matches > 0);
        assertEquals(matches, 2);
    }

    @Test
    public void givenNumberRange_whenMatchesAccurately_thenCorrect2() {
        int matches = runTest("[30-35]", "Two Uppercase alphabets 34 overall");
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenTwoSets_whenMatchesUnion_thenCorrect() {
        int matches = runTest("[1-3[7-9]]", "123456789");
        assertTrue(matches > 0);
        assertEquals(matches, 6);
    }

    @Test
    public void givenTwoSets_whenMatchesIntersection_thenCorrect() {
        int matches = runTest("[1-6&&[3-9]]", "123456789");
        assertTrue(matches > 0);
        assertEquals(matches, 4);
    }

    @Test
    public void givenSetWithSubtraction_whenMatchesAccurately_thenCorrect() {
        int matches = runTest("[0-9&&[^2468]]", "123456789");
        assertTrue(matches > 0);
        assertEquals(matches, 5);
    }

    @Test
    public void givenDigits_whenMatches_thenCorrect() {
        int matches = runTest("\\d", "123");
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    @Test
    public void givenNonDigits_whenMatches_thenCorrect() {
        int matches = runTest("\\D", "a6c");
        assertTrue(matches > 0);
        assertEquals(matches, 2);
    }

    @Test
    public void givenWhiteSpace_whenMatches_thenCorrect() {
        int matches = runTest("\\s", "a c");
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenNonWhiteSpace_whenMatches_thenCorrect() {
        int matches = runTest("\\S", "a c");
        assertTrue(matches > 0);
        assertEquals(matches, 2);
    }

    @Test
    public void givenWordCharacter_whenMatches_thenCorrect() {
        int matches = runTest("\\w", "hi!");
        assertTrue(matches > 0);
        assertEquals(matches, 2);
    }

    @Test
    public void givenNonWordCharacter_whenMatches_thenCorrect() {
        int matches = runTest("\\W", "hi!");
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenZeroOrOneQuantifier_whenMatches_thenCorrect() {
        int matches = runTest("\\a?", "hi");
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    @Test
    public void givenZeroOrOneQuantifier_whenMatches_thenCorrect2() {
        int matches = runTest("\\a{0,1}", "hi");
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    @Test
    public void givenZeroOrManyQuantifier_whenMatches_thenCorrect() {
        int matches = runTest("\\a*", "hi");
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    @Test
    public void givenZeroOrManyQuantifier_whenMatches_thenCorrect2() {
        int matches = runTest("\\a{0,}", "hi");
        assertTrue(matches > 0);
        assertEquals(matches, 3);
    }

    @Test
    public void givenOneOrManyQuantifier_whenMatches_thenCorrect() {
        int matches = runTest("\\a+", "hi");
        assertFalse(matches > 0);
    }

    @Test
    public void givenOneOrManyQuantifier_whenMatches_thenCorrect2() {
        int matches = runTest("\\a{1,}", "hi");
        assertFalse(matches > 0);
    }

    @Test
    public void givenBraceQuantifier_whenMatches_thenCorrect() {
        int matches = runTest("a{3}", "aaaaaa");
        assertTrue(matches > 0);
        assertEquals(matches, 2);
    }

    @Test
    public void givenBraceQuantifier_whenFailsToMatch_thenCorrect() {
        int matches = runTest("a{3}", "aa");
        assertFalse(matches > 0);
    }

    @Test
    public void givenBraceQuantifierWithRange_whenMatches_thenCorrect() {
        int matches = runTest("a{2,3}", "aaaa");
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenBraceQuantifierWithRange_whenMatchesLazily_thenCorrect() {
        int matches = runTest("a{2,3}?", "aaaa");
        assertTrue(matches > 0);
        assertEquals(matches, 2);
    }

    @Test
    public void givenCapturingGroup_whenMatches_thenCorrect() {
        int matches = runTest("(\\d\\d)", "12");
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenCapturingGroup_whenMatches_thenCorrect2() {
        int matches = runTest("(\\d\\d)", "1212");
        assertTrue(matches > 0);
        assertEquals(matches, 2);
    }

    @Test
    public void givenCapturingGroup_whenMatches_thenCorrect3() {
        int matches = runTest("(\\d\\d)(\\d\\d)", "1212");
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenCapturingGroup_whenMatchesWithBackReference_thenCorrect() {
        int matches = runTest("(\\d\\d)\\1", "1212");
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenCapturingGroup_whenMatchesWithBackReference_thenCorrect2() {
        int matches = runTest("(\\d\\d)\\1\\1\\1", "12121212");
        assertTrue(matches > 0);
        assertEquals(matches, 1);
    }

    @Test
    public void givenCapturingGroupAndWrongInput_whenMatchFailsWithBackReference_thenCorrect() {
        int matches = runTest("(\\d\\d)\\1", "1213");
        assertFalse(matches > 0);
    }

    @Test
    public void givenText_whenMatchesAtBeginning_thenCorrect() {
        int matches = runTest("^dog", "dogs are friendly");
        assertTrue(matches > 0);
    }

    @Test
    public void givenTextAndWrongInput_whenMatchFailsAtBeginning_thenCorrect() {
        int matches = runTest("^dog", "are dogs are friendly?");
        assertFalse(matches > 0);
    }

    @Test
    public void givenText_whenMatchesAtEnd_thenCorrect() {
        int matches = runTest("dog$", "Man's best friend is a dog");
        assertTrue(matches > 0);
    }

    @Test
    public void givenTextAndWrongInput_whenMatchFailsAtEnd_thenCorrect() {
        int matches = runTest("dog$", "is a dog man's best friend?");
        assertFalse(matches > 0);
    }

    @Test
    public void givenText_whenMatchesAtWordBoundary_thenCorrect() {
        int matches = runTest("\\bdog\\b", "a dog is friendly");
        assertTrue(matches > 0);
    }

    @Test
    public void givenText_whenMatchesAtWordBoundary_thenCorrect2() {
        int matches = runTest("\\bdog\\b", "dog is man's best friend");
        assertTrue(matches > 0);
    }

    @Test
    public void givenWrongText_whenMatchFailsAtWordBoundary_thenCorrect() {
        int matches = runTest("\\bdog\\b", "snoop dogg is a rapper");
        assertFalse(matches > 0);
    }

    @Test
    public void givenText_whenMatchesAtWordAndNonBoundary_thenCorrect() {
        int matches = runTest("\\bdog\\B", "snoop dogg is a rapper");
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegexWithoutCanonEq_whenMatchFailsOnEquivalentUnicode_thenCorrect() {
        int matches = runTest("\u00E9", "\u0065\u0301");
        assertFalse(matches > 0);
    }

    @Test
    public void givenRegexWithCanonEq_whenMatchesOnEquivalentUnicode_thenCorrect() {
        int matches = runTest("\u00E9", "\u0065\u0301", Pattern.CANON_EQ);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegexWithDefaultMatcher_whenMatchFailsOnDifferentCases_thenCorrect() {
        int matches = runTest("dog", "This is a Dog");
        assertFalse(matches > 0);
    }

    @Test
    public void givenRegexWithCaseInsensitiveMatcher_whenMatchesOnDifferentCases_thenCorrect() {
        int matches = runTest("dog", "This is a Dog", Pattern.CASE_INSENSITIVE);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegexWithEmbeddedCaseInsensitiveMatcher_whenMatchesOnDifferentCases_thenCorrect() {
        int matches = runTest("(?i)dog", "This is a Dog");
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegexWithComments_whenMatchFailsWithoutFlag_thenCorrect() {
        int matches = runTest("dog$  #check for word dog at end of text", "This is a dog");
        assertFalse(matches > 0);
    }

    @Test
    public void givenRegexWithComments_whenMatchesWithFlag_thenCorrect() {
        int matches = runTest("dog$  #check for word dog at end of text", "This is a dog", Pattern.COMMENTS);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegexWithComments_whenMatchesWithEmbeddedFlag_thenCorrect() {
        int matches = runTest("(?x)dog$  #check for word dog at end of text", "This is a dog");
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
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegex_whenMatchFailsWithLiteralFlag_thenCorrect() {
        int matches = runTest("(.*)", "text", Pattern.LITERAL);
        assertFalse(matches > 0);
    }

    @Test
    public void givenRegex_whenMatchesWithLiteralFlag_thenCorrect() {
        int matches = runTest("(.*)", "text(.*)", Pattern.LITERAL);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegex_whenMatchFailsWithoutMultilineFlag_thenCorrect() {
        int matches = runTest("dog$", "This is a dog" + System.getProperty("line.separator") + "this is a fox");
        assertFalse(matches > 0);
    }

    @Test
    public void givenRegex_whenMatchesWithMultilineFlag_thenCorrect() {
        int matches = runTest("dog$", "This is a dog" + System.getProperty("line.separator") + "this is a fox", Pattern.MULTILINE);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegex_whenMatchesWithEmbeddedMultilineFlag_thenCorrect() {
        int matches = runTest("(?m)dog$", "This is a dog" + System.getProperty("line.separator") + "this is a fox");
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

    @Test
    public void whenMatchesTenDigitsNumber_thenCorrect() {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher("1234567890");
        assertTrue(matcher.matches());
    }
    
    @Test
    public void whenMatchesTenDigitsNumberWhitespacesHyphen_thenCorrect() {
        Pattern pattern = Pattern.compile("^(\\d{3}[- ]?){2}\\d{4}$");
        Matcher matcher = pattern.matcher("123 456 7890");
        assertTrue(matcher.matches());
    }
    
    @Test
    public void whenMatchesTenDigitsNumberParenthesis_thenCorrect() {
        Pattern pattern = Pattern.compile("^\\(?\\d{3}\\)?[- ]?\\d{3}[- ]?\\d{4}$");
        Matcher matcher = pattern.matcher("(123)-456-7890");
        assertTrue(matcher.matches());
    }
    
    @Test
    public void whenMatchesTenDigitsNumberPrefix_thenCorrect() {
        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?\\(?\\d{3}\\)?[- ]?\\d{3}[- ]?\\d{4}$");
        Matcher matcher = pattern.matcher("+111 (123)-456-7890");
        assertTrue(matcher.matches());
    }
    
    @Test
    public void whenMatchesPhoneNumber_thenCorrect() {
        String patterns = "^(\\+\\d{1,3}( )?)?\\(?\\d{3}\\)?[- ]?\\d{3}[- ]?\\d{4}$" + 
                         "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" + 
                         "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
        
        String[] validPhoneNumbers = {"1234567890","123 456 7890", "(123)-456-7890", "+111 (123)-456-7890",
                                      "123 456 789", "+111 123 456 789", "123 45 67 89", "+111 123 45 67 89"};
        
        Pattern pattern = Pattern.compile(patterns);
        for(String phoneNumber : validPhoneNumbers) {
            Matcher matcher = pattern.matcher(phoneNumber);
            assertTrue(matcher.matches());
        }
    }

    public synchronized static int runTest(String regex, String text) {
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(text);
        int matches = 0;
        while (matcher.find())
            matches++;
        return matches;
    }

    public synchronized static int runTest(String regex, String text, int flags) {
        pattern = Pattern.compile(regex, flags);
        matcher = pattern.matcher(text);
        int matches = 0;
        while (matcher.find())
            matches++;
        return matches;
    }
}
