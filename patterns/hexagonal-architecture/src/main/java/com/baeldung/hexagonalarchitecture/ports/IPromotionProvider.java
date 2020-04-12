package com.baeldung.hexagonalarchitecture.ports;

import com.baeldung.hexagonalarchitecture.domain.Promotion;

import java.util.List;

public interface IPromotionProvider {

    List<Promotion> getPromotions();
}
