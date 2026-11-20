package com.baeldung.algorithms.sockmerchant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;

import org.junit.Test;

public class SockMerchantUnitTest {
    @Test
    public void givenSockArray_whenUsingArray_thenReturnsCorrectPairCount() {
        SockMerchant merchant = new SockMerchant();
        int[] socks = {11, 22, 22, 11, 33, 3, 33, 111111, 33, 222222};
        int colorMax = 222223;
        int actualPairs = merchant.countPairsWithArray(socks.length, socks, colorMax);
        assertEquals(3, actualPairs);
    }

    @Test
    public void givenSockArray_whenUsingSet_thenReturnsCorrectPairCount() {
        SockMerchant merchant = new SockMerchant();
        int[] socks = {11, 22, 22, 11, 33, 3, 33, 111111, 33, 222222};
        int actualPairs = merchant.countPairsWithSet(socks);
        assertEquals(3, actualPairs);
    }
}
