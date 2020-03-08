package com.baeldung.hexagonal.architecture.example.adapters.outbound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baeldung.hexagonal.architecture.example.application.domain.Cocktail;
import com.baeldung.hexagonal.architecture.example.application.ports.out.FetchCocktailsPort;
import com.baeldung.hexagonal.architecture.example.application.ports.out.SaveCocktailPort;

public class CocktailRepository implements FetchCocktailsPort, SaveCocktailPort {
    
    private Map<String, Cocktail> store = new HashMap<>();

    public List<Cocktail> fetchCocktails() {
        return new ArrayList<>(store.values());
    }

    public void saveCocktail(Cocktail cocktail) {
        store.put(cocktail.getName(), cocktail);
    }
}
