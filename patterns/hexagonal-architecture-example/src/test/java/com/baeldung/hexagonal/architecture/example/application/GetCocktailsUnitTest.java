package com.baeldung.hexagonal.architecture.example.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.hexagonal.architecture.example.application.domain.Cocktail;
import com.baeldung.hexagonal.architecture.example.application.ports.out.FetchCocktailsPort;
import com.baeldung.hexagonal.architecture.example.application.service.GetCocktailsService;

class GetCocktailsUnitTest {

    FetchCocktailsPort fetchCocktailsPort;
    GetCocktailsService getCocktailsService;

    @BeforeEach
    void setup() {
        fetchCocktailsPort = mock(FetchCocktailsPort.class);
        getCocktailsService = new GetCocktailsService(fetchCocktailsPort);
    }

    @Test
    void whenGettingCocktails_thenCocktailsFetchedFromPortAndReturned() {
        Cocktail cocktail = mock(Cocktail.class);
        when(cocktail.getName()).thenReturn("zombie");
        when(cocktail.getPrice()).thenReturn(12.00);
        when(fetchCocktailsPort.fetchCocktails()).thenReturn(List.of(cocktail));

        List<Cocktail> cocktails = getCocktailsService.getCocktails();
        
        assertEquals(1, cocktails.size());
        assertEquals(cocktail.getName(), cocktails.get(0).getName());
        assertEquals(cocktail.getPrice(), cocktails.get(0).getPrice());
    }

}
