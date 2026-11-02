package com.baeldung.springai.structuredoutput;

import java.util.List;

record Recipe(
    String name,
    String cuisine,
    Difficulty difficulty,
    int prepTimeMinutes,
    List<Ingredient> ingredients,
    List<String> steps
) {

    record Ingredient(
        String name,
        String quantity
    ) {}

    enum Difficulty { EASY, MEDIUM, HARD }
}