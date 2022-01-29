package com.baeldung.domain.ports;

import com.baeldung.domain.Recipe;

import java.util.Optional;

public interface ISmartRefrigerator {
    String whatIHave();
    Optional<Recipe> offerMeARecipe();
}
