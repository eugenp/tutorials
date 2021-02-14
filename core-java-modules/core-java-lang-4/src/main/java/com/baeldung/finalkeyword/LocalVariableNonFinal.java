package com.baeldung.finalkeyword;

public class LocalVariableNonFinal {

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
        String x = "x";
        String y = "y";
        return x + y;
    }

}
