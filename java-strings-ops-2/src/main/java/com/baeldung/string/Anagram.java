package com.baeldung.string;

import java.util.Arrays;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class Anagram {
    public boolean isAnagramSort(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        char[] a1 = str1.toCharArray();
        char[] a2 = str2.toCharArray();
        Arrays.sort(a1);
        Arrays.sort(a2);
        return Arrays.equals(a1, a2);
    }

    public boolean isAnagramCouting(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        final int MAX_CHARACTER = 256;
        int count[] = new int[MAX_CHARACTER];
        for (int i = 0; i < str1.length(); i++) {
            count[str1.charAt(i)]++;
            count[str2.charAt(i)]--;
        }
        for (int i = 0; i < MAX_CHARACTER; i++) {
            if (count[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnagramMultiset(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        Multiset<Character> multiset1 = HashMultiset.create();
        Multiset<Character> multiset2 = HashMultiset.create();
        for (int i = 0; i < str1.length(); i++) {
            multiset1.add(str1.charAt(i));
            multiset2.add(str2.charAt(i));
        }
        return multiset1.equals(multiset2);
    }

}
