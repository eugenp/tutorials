package com.baeldung.restoreipaddress;

import java.util.ArrayList;
import java.util.List;

public class DynamicProgramming implements RestoreIPAddress {
    public List<String> restoreIPAddresses(String s) {
        int n = s.length();
        if (n < 4 || n > 12) return new ArrayList<>();

        List<String>[][] dp = new ArrayList[n + 1][5];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= 4; j++) {
                dp[i][j] = new ArrayList<>();
            }
        }

        dp[0][0].add("");

        for (int i = 1; i <= n; i++) {
            for (int k = 1; k <= 4; k++) {
                for (int j = 1; j <= 3 && i - j >= 0; j++) {
                    String segment = s.substring(i - j, i);
                    if (isValid(segment)) {
                        for (String prefix : dp[i - j][k - 1]) {
                            dp[i][k].add(prefix.isEmpty() ? segment : prefix + "." + segment);
                        }
                    }
                }
            }
        }

        return dp[n][4];
    }

    private boolean isValid(String part) {
        if (part.length() > 1 && part.startsWith("0")) return false;
        int num = Integer.parseInt(part);
        return num >= 0 && num <= 255;
    }

}
