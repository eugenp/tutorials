package com.baeldung.algorithms.editdistance;

public class EditDistanceDynamicProgramming extends EditDistanceBase {

    static int calculate(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0)
                    dp[i][j] = j;

                else if (j == 0)
                    dp[i][j] = i;

                else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                        + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                      dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }
}
