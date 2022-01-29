package domain.ports;

import domain.Recipe;

import java.util.Optional;

public interface ISmartRefrigerator {
    String whatIHave();
    Optional<Recipe> offerMeARecipe();
}
