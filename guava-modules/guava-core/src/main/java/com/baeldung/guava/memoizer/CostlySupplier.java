package com.baeldung.guava.memoizer;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CostlySupplier {

    public static BigInteger generateBigNumber() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
        }
        return new BigInteger("12345");
    }

}
