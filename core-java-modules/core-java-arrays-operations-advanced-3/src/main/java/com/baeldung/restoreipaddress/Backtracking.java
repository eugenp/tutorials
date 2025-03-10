package com.baeldung.restoreipaddress;

import java.util.ArrayList;
import java.util.List;

public class Backtracking implements RestoreIPAddress {
    public List<String> restoreIPAddresses(String s) {
        List<String> result = new ArrayList<>();
        backtrack(result, s, new ArrayList<>(), 0);
        return result;
    }

    private void backtrack(List<String> result, String s, List<String> current, int index) {
        if (current.size() == 4) {
            if (index == s.length()) {
                result.add(String.join(".", current));
            }
            return;
        }

        for (int len = 1; len <= 3; len++) {
            if (index + len > s.length()) break;

            String part = s.substring(index, index + len);
            if (isValid(part)) {
                current.add(part);
                backtrack(result, s, current, index + len);
                current.remove(current.size() - 1);
            }
        }
    }

    private boolean isValid(String part) {
        if (part.length() > 1 && part.startsWith("0")) return false;
        int num = Integer.parseInt(part);
        return num >= 0 && num <= 255;
    }
}

