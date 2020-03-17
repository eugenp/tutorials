package com.baeldung.hexagonal.architecture.example.application.ports.in;

import java.util.List;

import com.baeldung.hexagonal.architecture.example.application.domain.Cocktail;

public interface GetCocktailsPort {
    List<Cocktail> getCocktails();
}
