package com.baeldung.hexagonal.arch.commandLayer;

import java.util.Scanner;

import com.baeldung.hexagonal.arch.mainLayer.service.BookService;
import com.baeldung.hexagonal.arch.mainLayer.service.BookServiceImpl;

public class UserChoiceImpl implements UserChoice {
    private BookService bookService = new BookServiceImpl();

    @Override
    public void getInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to buy or sell a book? \n" + "Enter B for Buy & S for Sell: ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("B")) {
            bookService.buyABook();
        } else if (choice.equalsIgnoreCase("S")) {
            bookService.sellABook();
        }
        sc.close();
    }
}
