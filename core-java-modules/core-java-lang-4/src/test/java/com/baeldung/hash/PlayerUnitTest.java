package com.baeldung.hash;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class PlayerUnitTest {

    @Test
    public void whenCallingHashCodeOnIdenticalValue_thenSameHashCodeReturned() {
        Player player = new Player("Eduardo", "Rodriguez", "Pitcher");
        Player indenticalPlayer = new Player("Eduardo", "Rodriguez", "Pitcher");

        int hashCode1 = player.hashCode();
        int hashCode2 = player.hashCode();
        int hashCode3 = indenticalPlayer.hashCode();

        assertEquals(hashCode1, hashCode2);
        assertEquals(hashCode1, hashCode3);
    }

    @Test
    public void whenCallingHashCodeAndArraysHashCode_thenSameHashCodeReturned() {
        Player player = new Player("Bobby", "Dalbec", "First Base");
        int hashcode1 = player.hashCode();
        String[] playerInfo = { "Bobby", "Dalbec", "First Base" };
        int hashcode2 = Arrays.hashCode(playerInfo);

        assertEquals(hashcode1, hashcode2);
    }
}
