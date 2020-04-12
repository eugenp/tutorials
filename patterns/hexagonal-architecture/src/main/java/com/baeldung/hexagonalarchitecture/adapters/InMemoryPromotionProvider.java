package com.baeldung.hexagonalarchitecture.adapters;

import com.baeldung.hexagonalarchitecture.domain.Promotion;
import com.baeldung.hexagonalarchitecture.ports.IPromotionProvider;

import java.util.Arrays;
import java.util.List;

/**
 * Adapter is implementing the IPromotionProvider interface to manage Promotion I/O in memory
 */
public class InMemoryPromotionProvider implements IPromotionProvider {

    private static List<Promotion> PROMOTIONS = Arrays.asList(
            new Promotion(1, 5.00),
            new Promotion(2, 3.00),
            new Promotion(3, 2.00)
    );

    @Override
    public List<Promotion> getPromotions() {
        return PROMOTIONS;
    }
}
