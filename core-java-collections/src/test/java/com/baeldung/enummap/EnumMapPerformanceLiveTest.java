package com.baeldung.enummap;

import org.junit.Test;

import java.util.EnumMap;
import java.util.HashMap;

public class EnumMapPerformanceLiveTest {
    @Test
    public void givenEnumMap_whenComparedToHashMap_thenEnumMapFaster() {

        EnumMap<Club, String> clubMap = new EnumMap<>(Club.class);

        //        clubMap.put(Club.Juventus, "abcdefg");
        //        clubMap.put(Club.PSG, "hijklmno");
        //        clubMap.put(Club.Madrid, "pqrstuvw");
        //        clubMap.put(Club.Liverpool, "vwxyz12");

        HashMap<Club, String> clubHashMap = new HashMap<>();

        Club[] clubs = Club.values();

        //        clubHashMap.put(Club.Juventus, "abcdefg");
        //        clubHashMap.put(Club.PSG, "hijklmno");
        //        clubHashMap.put(Club.Madrid, "pqrstuvw");
        //        clubHashMap.put(Club.Liverpool, "vwxyz12");

        int length = clubs.length;
        for(int i = 0; i< length; i++) {
            clubMap.put(clubs[i], clubs[i].name());
            clubHashMap.put(clubs[i], clubs[i].name());
        }

        assert clubMap.size() == length;
        assert clubHashMap.size() == length;

        long startTime = System.nanoTime();
        for (int i = 0, j = 0; i < 1000000; i++, j++) {
            if (j >= length)
                j = 0;
            Club club = clubs[j];
            clubHashMap.get(club);
        }
        long timeEnumMap = System.nanoTime() - startTime;
        System.out.println("HashMap :"+timeEnumMap);

        startTime = System.nanoTime();
        for (int i = 0, j = 0; i < 1000000; i++, j++) {
            if (j >= length)
                j = 0;
            Club club = clubs[j];
            clubMap.get(club);
        }
        long timeHashMap = System.nanoTime() - startTime;
        System.out.println("EnumMap :"+timeHashMap);
    }

    public enum Club {
        Juventus, PSG, Madrid, Liverpool, Milan, Lyon, Barcelona, MU
    }
}
