package com.baeldung.algorithms.sockmerchant;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;

public class SockMerchant {
    public int countPairsWithArray(int n, @Nonnull int[] socks, int k) {
        int[] counts = new int[k];
        int pairs = 0;
        for (int i = 0; i < n; i++) {
            counts[socks[i]]++;
        }
        for (int count : counts) {
            pairs += count / 2;
        }
        return pairs;
    }

    public int countPairsWithSet(@Nonnull int[] socks) {
        Set<Integer> unmatchedSocks = new HashSet<>();
        int pairs = 0;
        for (int sock : socks) {
            if (unmatchedSocks.contains(sock)) {
                pairs++;
                unmatchedSocks.remove(sock);
            } else {
                unmatchedSocks.add(sock);
            }
        }
        return pairs;
    }
}
