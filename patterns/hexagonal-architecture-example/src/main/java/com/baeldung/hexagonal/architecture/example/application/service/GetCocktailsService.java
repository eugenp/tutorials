package com.baeldung.hexagonal.architecture.example.application.service;

import java.util.List;

import com.baeldung.hexagonal.architecture.example.application.domain.Cocktail;
import com.baeldung.hexagonal.architecture.example.application.ports.in.GetCocktailsPort;
import com.baeldung.hexagonal.architecture.example.application.ports.out.FetchCocktailsPort;

public class GetCocktailsService implements GetCocktailsPort {
    private FetchCocktailsPort fetchCocktailsPort;

    public GetCocktailsService(FetchCocktailsPort fetchCocktailsPort) {
        this.fetchCocktailsPort = fetchCocktailsPort;
    }

    @Override
    public List<Cocktail> getCocktails() {
        return fetchCocktailsPort.fetchCocktails();
    }
}
