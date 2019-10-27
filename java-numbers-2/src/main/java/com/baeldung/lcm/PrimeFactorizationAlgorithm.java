package com.baeldung.lcm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PrimeFactorizationAlgorithm {

    public static Map<Integer, Integer> getPrimeFactors(int number) {
        int absNumber = Math.abs(number);
        Map<Integer, Integer> primeFactorsMap = new HashMap<Integer, Integer>();
        for (int factor = 2; factor <= absNumber; factor++) {
            while (absNumber % factor == 0) {
                Integer power = primeFactorsMap.get(factor);
                if (power == null) {
                    power = 0;
                }
                primeFactorsMap.put(factor, power + 1);
                absNumber /= factor;
            }
        }
        return primeFactorsMap;
    }

    public static int lcm(int number1, int number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        Map<Integer, Integer> primeFactorsForNum1 = getPrimeFactors(number1);
        Map<Integer, Integer> primeFactorsForNum2 = getPrimeFactors(number2);
        Set<Integer> primeFactorsUnionSet = new HashSet<Integer>(primeFactorsForNum1.keySet());
        primeFactorsUnionSet.addAll(primeFactorsForNum2.keySet());
        int lcm = 1;
        for (Integer primeFactor : primeFactorsUnionSet) {
            lcm *= Math.pow(primeFactor, Math.max(primeFactorsForNum1.getOrDefault(primeFactor, 0),
                    primeFactorsForNum2.getOrDefault(primeFactor, 0)));
        }
        return lcm;
    }

}
