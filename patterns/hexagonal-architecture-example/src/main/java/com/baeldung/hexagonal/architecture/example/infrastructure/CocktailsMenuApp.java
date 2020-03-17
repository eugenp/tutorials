package com.baeldung.hexagonal.architecture.example.infrastructure;

import java.util.Scanner;

import com.baeldung.hexagonal.architecture.example.adapters.inbound.CommandLineAdapter;
import com.baeldung.hexagonal.architecture.example.adapters.outbound.CocktailRepository;
import com.baeldung.hexagonal.architecture.example.application.service.AddCocktailService;
import com.baeldung.hexagonal.architecture.example.application.service.GetCocktailsService;

public class CocktailsMenuApp {
    private static final String ADD = "add";
    private static final String LIST = "list";
    private static final String EXIT = "exit";
    private static final String HELP_TEXT = "Enter command \n" + 
        ADD + " - add a new Cocktail to the menu \n" + 
        LIST + " - list cocktails \n" + 
        EXIT + " - exit \n";
    private static Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        CocktailRepository cocktailRepository = new CocktailRepository();
        AddCocktailService addCocktailService = new AddCocktailService(cocktailRepository);
        GetCocktailsService getCocktailsMenuService = new GetCocktailsService(cocktailRepository);
        CommandLineAdapter commandLineAdapter = new CommandLineAdapter(SCANNER, addCocktailService, getCocktailsMenuService);
        runWith(commandLineAdapter);
    }

    public static void runWith(CommandLineAdapter adapter) {
        String command = null;
        while (!EXIT.equalsIgnoreCase(command)) {
            System.out.println(HELP_TEXT);
            command = SCANNER.next();
            switch (command.toLowerCase()) {
            case ADD:
                adapter.addCocktail();
                break;
            case LIST:
                adapter.listCocktails();
                break;
            }
        }
    }
}
