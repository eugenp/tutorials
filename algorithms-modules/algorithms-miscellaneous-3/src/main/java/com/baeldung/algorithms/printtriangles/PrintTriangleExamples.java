package com.baeldung.algorithms.printtriangles;

import org.apache.commons.lang3.StringUtils;

public class PrintTriangleExamples {

    public static String printARightTriangle(int N) {
        StringBuilder result = new StringBuilder();
        for (int r = 1; r <= N; r++) {
            for (int j = 1; j <= r; j++) {
                result.append("*");
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    public static String printAnIsoscelesTriangle(int N) {
        StringBuilder result = new StringBuilder();
        for (int r = 1; r <= N; r++) {
            for (int sp = 1; sp <= N - r; sp++) {
                result.append(" ");
            }
            for (int c = 1; c <= (r * 2) - 1; c++) {
                result.append("*");
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    public static String printAnIsoscelesTriangleUsingStringUtils(int N) {
        StringBuilder result = new StringBuilder();

        for (int r = 1; r <= N; r++) {
            result.append(StringUtils.repeat(' ', N - r));
            result.append(StringUtils.repeat('*', 2 * r - 1));
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    public static String printAnIsoscelesTriangleUsingSubstring(int N) {
        StringBuilder result = new StringBuilder();
        String helperString = StringUtils.repeat(' ', N - 1) + StringUtils.repeat('*', N * 2 - 1);

        for (int r = 0; r < N; r++) {
            result.append(helperString.substring(r, N + 2 * r));
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(printARightTriangle(5));
        System.out.println(printAnIsoscelesTriangle(5));
        System.out.println(printAnIsoscelesTriangleUsingStringUtils(5));
        System.out.println(printAnIsoscelesTriangleUsingSubstring(5));
    }

}