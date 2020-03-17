package com.baeldung.hexagonal.architecture.example.adapters.inbound;

import java.util.List;
import java.util.Scanner;

import com.baeldung.hexagonal.architecture.example.application.domain.Cocktail;
import com.baeldung.hexagonal.architecture.example.application.ports.in.AddCocktailPort;
import com.baeldung.hexagonal.architecture.example.application.ports.in.AddCocktailPort.AddCocktailCommand;
import com.baeldung.hexagonal.architecture.example.application.ports.in.GetCocktailsPort;

public class CommandLineAdapter {
    private AddCocktailPort addCocktailPort;
    private GetCocktailsPort listCocktailsPort;
    private Scanner scanner;

    public CommandLineAdapter(Scanner scanner, AddCocktailPort addCocktailPort, GetCocktailsPort listCocktailsPort) {
        this.addCocktailPort = addCocktailPort;
        this.listCocktailsPort = listCocktailsPort;
        this.scanner = scanner;
    }

    public void addCocktail() {
        try {
            System.out.println("Enter the name of the cocktail");
            scanner.nextLine();
            String name = scanner.nextLine();
            System.out.println("Enter the price of the cocktail");
            double price = scanner.nextDouble();
            scanner.nextLine();
            AddCocktailCommand command = new AddCocktailCommand(name, price);
            addCocktailPort.addCocktail(command);
        } catch (Exception exception) {
            System.out.println("Error during adding a new Cocktail");
        }
    }

    public void listCocktails() {
        List<Cocktail> cocktailList = listCocktailsPort.getCocktails();
        System.out.println("Cocktails menu: \n");
        cocktailList.stream()
            .forEach(cocktail -> {
                System.out.println(cocktail.getName() + "\n" + 
                    "price: " + cocktail.getPrice() + " $ " + "\n");
            });
    }
}
