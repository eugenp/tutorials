package com.baeldung.firstnonrepeatingcharacter;

import org.junit.Assert;
import org.junit.Test;

public class FirstNonRepeatingCharacterTest {

    @Test
    public void testNonRepeatingCharacterBruteForce(){
        FirstNonRepeatingCharacter program = new FirstNonRepeatingCharacter();

        Assert.assertEquals(program.firstNonRepeatingCharBruteForce("baeldung"), Character.valueOf('b'));
        Assert.assertEquals(program.firstNonRepeatingCharBruteForce("lullaby"), Character.valueOf('u'));
        Assert.assertEquals(program.firstNonRepeatingCharBruteForce("hello"), Character.valueOf('h'));
        Assert.assertEquals(program.firstNonRepeatingCharBruteForce("mahimahi"), null);
        Assert.assertEquals(program.firstNonRepeatingCharBruteForce(""), null);
        Assert.assertEquals(program.firstNonRepeatingCharBruteForce(null), null);
    }

    @Test
    public void testNonRepeatingCharacterBruteForceNaive(){
        FirstNonRepeatingCharacter program = new FirstNonRepeatingCharacter();
        Assert.assertEquals(program.firstNonRepeatingCharBruteForceNaive("baeldung"), Character.valueOf('b'));
        Assert.assertEquals(program.firstNonRepeatingCharBruteForceNaive("lullaby"), Character.valueOf('u'));
        Assert.assertEquals(program.firstNonRepeatingCharBruteForceNaive("hello"), Character.valueOf('h'));
        Assert.assertEquals(program.firstNonRepeatingCharBruteForceNaive("mahimahi"), null);
        Assert.assertEquals(program.firstNonRepeatingCharBruteForceNaive(""), null);
        Assert.assertEquals(program.firstNonRepeatingCharBruteForceNaive(null), null);
    }

    @Test
    public void testNonRepeatingCharacterWithMap(){
        FirstNonRepeatingCharacter program = new FirstNonRepeatingCharacter();
        Assert.assertEquals(program.firstNonRepeatingCharWithMap("baeldung"), Character.valueOf('b'));
        Assert.assertEquals(program.firstNonRepeatingCharWithMap("lullaby"), Character.valueOf('u'));
        Assert.assertEquals(program.firstNonRepeatingCharWithMap("hello"), Character.valueOf('h'));
        Assert.assertEquals(program.firstNonRepeatingCharWithMap("mahimahi"), null);
        Assert.assertEquals(program.firstNonRepeatingCharWithMap(""), null);
        Assert.assertEquals(program.firstNonRepeatingCharWithMap(null), null);
    }

    @Test
    public void testNonRepeatingCharacterWithArray(){
        FirstNonRepeatingCharacter program = new FirstNonRepeatingCharacter();
        Assert.assertEquals(program.firstNonRepeatingCharWithArray("baeldung"), Character.valueOf('b'));
        Assert.assertEquals(program.firstNonRepeatingCharWithArray("lullaby"), Character.valueOf('u'));
        Assert.assertEquals(program.firstNonRepeatingCharWithArray("hello"), Character.valueOf('h'));
        Assert.assertEquals(program.firstNonRepeatingCharWithArray("mahimahi"), null);
        Assert.assertEquals(program.firstNonRepeatingCharWithArray(""), null);
        Assert.assertEquals(program.firstNonRepeatingCharWithArray(null), null);
    }
}
