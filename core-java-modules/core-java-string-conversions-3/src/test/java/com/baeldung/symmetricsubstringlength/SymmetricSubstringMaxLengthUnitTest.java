package com.baeldung.symmetricsubstringlength;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SymmetricSubstringMaxLengthUnitTest {
    String input = "<><??>>";
    int expected = 4;

    @Test
    public void givenString_whenUsingSymmetricSubstringExpansion_thenFindLongestSymmetricSubstring() {
        int start = 0;
        int mid = 0;
        int last_gt = 0;
        int end = 0;
        int best = 0;

        while (start < input.length()) {
            int current = Math.min(mid - start, end - mid);
            if (best < current) {
                best = current;
            }

            if (end - mid == current && end < input.length()) {
                if (input.charAt(end) == '?') {
                    end++;
                } else if (input.charAt(end) == '>') {
                    end++;
                    last_gt = end;
                } else {
                    end++;
                    mid = end;
                    start = Math.max(start, last_gt);
                }
            } else if (mid < input.length() && input.charAt(mid) == '?') {
                mid++;
            } else if (start < mid) {
                start++;
            } else {
                start = Math.max(start, last_gt);
                mid++;
                end = Math.max(mid, end);
            }
        }
        int result = 2 * best;

        assertEquals(expected, result);
    }

    @Test
    public void givenString_whenUsingBruteForce_thenFindLongestSymmetricSubstring() {
        int max = 0;
        for (int i = 0; i < input.length(); i++) {
            for (int j = i + 1; j <= input.length(); j++) {
                String t = input.substring(i, j);
                if (t.length() % 2 == 0) {
                    int k = 0, l = t.length() - 1;
                    boolean isSym = true;
                    while (k < l && isSym) {
                        if (!(t.charAt(k) == '<' || t.charAt(k) == '?') && (t.charAt(l) == '>' || t.charAt(l) == '?')) {
                            isSym = false;
                        }
                        k++;
                        l--;
                    }
                    if (isSym) {
                        max = Math.max(max, t.length());
                    }
                }
            }
        }

        assertEquals(expected, max);
    }

}
