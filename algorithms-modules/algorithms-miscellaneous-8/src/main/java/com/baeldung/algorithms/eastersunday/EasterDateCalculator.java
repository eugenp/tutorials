package com.baeldung.algorithms.eastersunday;

import java.time.LocalDate;

public class EasterDateCalculator {
    
    LocalDate computeEasterDateWithGaussAlgorithm(int year) {
        int a = year % 19;
        int b = year % 4;
        int c = year % 7;
        int k = year / 100;
        int p = (13 + 8*k) / 25;
        int q = k / 4;
        int M = (15 - p + k - q) % 30;
        int N = (4 + k - q) % 7;
        int d = (19*a + M) % 30;
        int e = (2*b + 4*c + 6*d + N) % 7;
        
        if (d==29 && e == 6) {
            return LocalDate.of(year, 4, 19);
        } else if (d==28 && e==6 && ((11*M + 11)%30 < 10)) {
            return LocalDate.of(year, 4, 18);
        }
        
        int H = 22 + d + e;
        if (H <= 31) {
            return LocalDate.of(year, 3, H);
        }
        return LocalDate.of(year, 4, H-31);
    }
    
    LocalDate computeEasterDateWithButcherMeeusAlgorithm(int year) {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19*a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (2*e + 2*i - h - k + 32) % 7;
        int m = (a + 11*h + 22*l) / 451;
        int t = h + l - 7*m + 114;
        int n = t / 31;
        int o = t % 31;
        return LocalDate.of(year, n, o+1);
    }

    LocalDate computeEasterDateWithConwayAlgorithm(int year) {
        int s = year / 100;
        int t = year % 100;
        int a = t / 4;
        int p = s % 4;
        int x = (9 - 2*p) % 7;
        int y = (x + t + a) % 7;
        int g = year % 19;
        int G = g + 1;
        int b = s / 4;
        int r = 8 * (s + 11) / 25;
        int C = -s + b + r;
        int d = (11*G + C) % 30;
        d = (d + 30) % 30;
        int h = (551 - 19*d + G) / 544;
        int e = (50 - d - h) % 7;
        int f = (e + y) % 7;
        int R = 57 - d - f - h;
        
        if (R <= 31) {
            return LocalDate.of(year, 3, R);
        }
        return LocalDate.of(year, 4, R-31);
    }
    
}
