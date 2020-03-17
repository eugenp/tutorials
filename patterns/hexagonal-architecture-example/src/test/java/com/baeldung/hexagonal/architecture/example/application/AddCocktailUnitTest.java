package com.baeldung.hexagonal.architecture.example.application;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.baeldung.hexagonal.architecture.example.application.domain.Cocktail;
import com.baeldung.hexagonal.architecture.example.application.ports.in.AddCocktailPort.AddCocktailCommand;
import com.baeldung.hexagonal.architecture.example.application.ports.out.SaveCocktailPort;
import com.baeldung.hexagonal.architecture.example.application.service.AddCocktailService;

class AddCocktailUnitTest {
    
    SaveCocktailPort saveCocktailPort;
    AddCocktailService addCocktailService;
    
    @BeforeEach
    void setup() {
        saveCocktailPort = mock(SaveCocktailPort.class);
        addCocktailService = new AddCocktailService(saveCocktailPort); 
    }
    
    @Test
    void whenAddingACocktail_thenTheCocktailIsSaved() {
        AddCocktailCommand command = mock(AddCocktailCommand.class);
        when(command.getName()).thenReturn("mockito");
        when(command.getPrice()).thenReturn(9.50);
        ArgumentCaptor<Cocktail> captor = ArgumentCaptor.forClass(Cocktail.class);
        
        addCocktailService.addCocktail(command);
        
        Mockito.verify(saveCocktailPort).saveCocktail(captor.capture());
        Cocktail cocktailThatSaved = captor.getValue();
        assertEquals(command.getName(), cocktailThatSaved.getName());
        assertEquals(command.getPrice(), cocktailThatSaved.getPrice());
    }

}
