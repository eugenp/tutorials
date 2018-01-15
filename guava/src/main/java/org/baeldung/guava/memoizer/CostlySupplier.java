package org.baeldung.guava.memoizer;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CostlySupplier {

    public static BigInteger generateBigNumber() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
        }
        BigInteger number = BigInteger.valueOf(new Random(0L)
                .ints(0, Integer.MAX_VALUE)
                .limit(1)
                .findFirst()
                .getAsInt());
        return number.multiply(number).pow(5);
    }

}
