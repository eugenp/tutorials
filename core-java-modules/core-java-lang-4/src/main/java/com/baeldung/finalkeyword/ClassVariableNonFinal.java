package com.baeldung.finalkeyword;

public class ClassVariableNonFinal {

    static String x = "x";
    static String y = "y";

    public static void main(String[] args) {
        for (int i = 0; i < 1500; i++) {
            long startTime = System.nanoTime();
            String result = concatStrings();
            long totalTime = System.nanoTime() - startTime;
            if (i >= 500) {
                System.out.println(totalTime);
            }
        }
    }

    private static String concatStrings() {
        return x + y;
    }

}
