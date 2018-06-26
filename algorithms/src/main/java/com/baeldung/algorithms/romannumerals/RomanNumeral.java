package com.baeldung.algorithms.romannumerals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

enum RomanNumeral {
    I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100), CD(400), D(500), CM(900), M(1000);

    private int value;

    RomanNumeral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static List<RomanNumeral> getSortedValues() {
        List<RomanNumeral> allValues = Arrays.asList(values());
        
        Collections.sort(allValues, new Comparator<RomanNumeral>() {
            @Override
            public int compare(RomanNumeral x, RomanNumeral y) {
                return x.value - y.value;
            }
        });
        
        return allValues;
    }
}
