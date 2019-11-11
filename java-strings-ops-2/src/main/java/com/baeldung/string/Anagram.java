package com.baeldung.string;

import java.util.Arrays;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class Anagram {
    //this definition only works for single byte encoding character set.
    //For multibyte encoding, such as UTF-8, 16, 32 etc., 
    //we need to increase this number so that it can contain all possible characters.
    private static int MAX_CHARACTER = 256;

    public boolean isAnagramSort(String string1, String string2) {
        if (string1.length() != string2.length()) {
            return false;
        }
        char[] a1 = string1.toCharArray();
        char[] a2 = string2.toCharArray();
        Arrays.sort(a1);
        Arrays.sort(a2);
        return Arrays.equals(a1, a2);
    }

    public boolean isAnagramCounting(String string1, String string2) {
        if (string1.length() != string2.length()) {
            return false;
        }
        int count[] = new int[MAX_CHARACTER];
        for (int i = 0; i < string1.length(); i++) {
            count[string1.charAt(i)]++;
            count[string2.charAt(i)]--;
        }
        for (int i = 0; i < MAX_CHARACTER; i++) {
            if (count[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnagramMultiset(String string1, String string2) {
        if (string1.length() != string2.length()) {
            return false;
        }
        Multiset<Character> multiset1 = HashMultiset.create();
        Multiset<Character> multiset2 = HashMultiset.create();
        for (int i = 0; i < string1.length(); i++) {
            multiset1.add(string1.charAt(i));
            multiset2.add(string2.charAt(i));
        }
        return multiset1.equals(multiset2);
    }

}
