package com.baeldung.hexagonal.architecture.example.application.service;

import com.baeldung.hexagonal.architecture.example.application.domain.Cocktail;
import com.baeldung.hexagonal.architecture.example.application.ports.in.AddCocktailPort;
import com.baeldung.hexagonal.architecture.example.application.ports.out.SaveCocktailPort;

public class AddCocktailService implements AddCocktailPort {
    private SaveCocktailPort saveCocktailPort;

    public AddCocktailService(SaveCocktailPort saveCocktailPort) {
        this.saveCocktailPort = saveCocktailPort;
    }

    @Override
    public void addCocktail(AddCocktailCommand command) {
        Cocktail cocktail = new Cocktail(command.getName(), command.getPrice());
        saveCocktailPort.saveCocktail(cocktail);
    }
}
