package com.example;

public class ManualIterationApproach {

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder(
            "StringBuilder\nManual Iteration Approach\r\nLine by Line Reading\rAnother line"
        );

        int start = 0;

        for (int i = 0; i < sb.length(); i++) {

            char c = sb.charAt(i);

            if (c == '\n' || c == '\r') {

                System.out.println(sb.substring(start, i));

                if (c == '\r' && i + 1 < sb.length() && sb.charAt(i + 1) == '\n') {
                    i++;
                }

                start = i + 1;
            }
        }

        if (start < sb.length()) {
            System.out.println(sb.substring(start));
        }
    }
}
