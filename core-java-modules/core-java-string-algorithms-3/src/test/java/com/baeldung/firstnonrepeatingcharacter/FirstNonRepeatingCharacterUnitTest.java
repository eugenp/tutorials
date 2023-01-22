package com.baeldung.firstnonrepeatingcharacter;

import org.junit.Assert;
import org.junit.Test;

public class FirstNonRepeatingCharacterUnitTest {

    @Test
    public void testNonRepeatingCharacterBruteForce() {
        FirstNonRepeatingCharacter program = new FirstNonRepeatingCharacter();

        Assert.assertEquals(program.firstNonRepeatingCharBruteForce("baeldung"), Character.valueOf('b'));
        Assert.assertEquals(program.firstNonRepeatingCharBruteForce("lullaby"), Character.valueOf('u'));
        Assert.assertEquals(program.firstNonRepeatingCharBruteForce("hello"), Character.valueOf('h'));
        Assert.assertNull(program.firstNonRepeatingCharBruteForce("mahimahi"));
        Assert.assertNull(program.firstNonRepeatingCharBruteForce(""));
        Assert.assertNull(program.firstNonRepeatingCharBruteForce(null));
    }

    @Test
    public void testNonRepeatingCharacterBruteForceNaive() {
        FirstNonRepeatingCharacter program = new FirstNonRepeatingCharacter();
        Assert.assertEquals(program.firstNonRepeatingCharBruteForceNaive("baeldung"), Character.valueOf('b'));
        Assert.assertEquals(program.firstNonRepeatingCharBruteForceNaive("lullaby"), Character.valueOf('u'));
        Assert.assertEquals(program.firstNonRepeatingCharBruteForceNaive("hello"), Character.valueOf('h'));
        Assert.assertNull(program.firstNonRepeatingCharBruteForceNaive("mahimahi"));
        Assert.assertNull(program.firstNonRepeatingCharBruteForceNaive(""));
        Assert.assertNull(program.firstNonRepeatingCharBruteForceNaive(null));
    }

    @Test
    public void testNonRepeatingCharacterWithMap() {
        FirstNonRepeatingCharacter program = new FirstNonRepeatingCharacter();
        Assert.assertEquals(program.firstNonRepeatingCharWithMap("baeldung"), Character.valueOf('b'));
        Assert.assertEquals(program.firstNonRepeatingCharWithMap("lullaby"), Character.valueOf('u'));
        Assert.assertEquals(program.firstNonRepeatingCharWithMap("hello"), Character.valueOf('h'));
        Assert.assertNull(program.firstNonRepeatingCharWithMap("mahimahi"));
        Assert.assertNull(program.firstNonRepeatingCharWithMap(""));
        Assert.assertNull(program.firstNonRepeatingCharWithMap(null));
    }

    @Test
    public void testNonRepeatingCharacterWithArray() {
        FirstNonRepeatingCharacter program = new FirstNonRepeatingCharacter();
        Assert.assertEquals(program.firstNonRepeatingCharWithArray("baeldung"), Character.valueOf('b'));
        Assert.assertEquals(program.firstNonRepeatingCharWithArray("lullaby"), Character.valueOf('u'));
        Assert.assertEquals(program.firstNonRepeatingCharWithArray("hello"), Character.valueOf('h'));
        Assert.assertNull(program.firstNonRepeatingCharWithArray("mahimahi"));
        Assert.assertNull(program.firstNonRepeatingCharWithArray(""));
        Assert.assertNull(program.firstNonRepeatingCharWithArray(null));
    }
}