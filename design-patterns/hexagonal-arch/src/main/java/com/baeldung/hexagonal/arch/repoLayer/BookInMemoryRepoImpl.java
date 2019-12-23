package com.baeldung.hexagonal.arch.repoLayer;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.baeldung.hexagonal.arch.mainLayer.domain.Book;
import com.baeldung.hexagonal.arch.mainLayer.repo.BookInMemoryRepo;

public class BookInMemoryRepoImpl implements BookInMemoryRepo {
    private List<Book> purchasedBooksList = new LinkedList<>();
    private List<Book> soldBooksList = new LinkedList<>();

    public Book getInfo(Book book) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the book name: ");
        book.setBookName(sc.nextLine());
        System.out.println("Enter the book author: ");
        book.setBookAuthor(sc.nextLine());
        System.out.println("Enter the book ISBN number: ");
        book.setBookISBN(sc.nextLine());
        System.out.println("Enter the book price: ");
        book.setBookPrice(sc.nextDouble());
        sc.close();
        return book;
    }

    @Override
    public void purchase() {
        Book book = getInfo(new Book());
        purchasedBooksList.add(book);
        System.out.println("\nThe book " + book.getBookName() + " has been purchased by you.");
    }

    @Override
    public void sell() {
        Book book = getInfo(new Book());
        soldBooksList.add(book);
        System.out.println("\nThe book " + book.getBookName() + " has been sold by you.");
    }
}
