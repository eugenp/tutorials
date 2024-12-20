package com.baeldung.restoreipaddress;

import java.util.ArrayList;
import java.util.List;

public class Iterative {
    public List<String> restoreIPAddresses(String s) {
        List<String> result = new ArrayList<>();

        if (s.length() < 4 || s.length() > 12) {
            return result;
        }

        for (int i = 1; i < 4; i++) {
            for (int j = i + 1; j < Math.min(i + 4, s.length()); j++) {
                for (int k = j + 1; k < Math.min(j + 4, s.length()); k++) {
                    String part1 = s.substring(0, i);
                    String part2 = s.substring(i, j);
                    String part3 = s.substring(j, k);
                    String part4 = s.substring(k);

                    if (isValid(part1) && isValid(part2) && isValid(part3) && isValid(part4)) {
                        result.add(part1 + "." + part2 + "." + part3 + "." + part4);
                    }
                }
            }
        }
        return result;
    }

    private boolean isValid(String part) {
        if (part.length() > 1 && part.charAt(0) == '0') {
            return false;
        }
        int num = Integer.parseInt(part);
        return num >= 0 && num <= 255;
    }
}
