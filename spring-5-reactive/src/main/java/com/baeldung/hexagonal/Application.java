package com.baeldung.hexagonal;


import com.baeldung.hexagonal.adapter.CommandLineLibraryInput;
import com.baeldung.hexagonal.adapter.InMemoryLibraryRepository;
import com.baeldung.hexagonal.application.LibraryInput;
import com.baeldung.hexagonal.application.LibraryRegistry;
import com.baeldung.hexagonal.application.LibraryRepository;

public class Application {

    public static void main(String[] args) {

        LibraryInput libraryInput = new CommandLineLibraryInput();
        LibraryRepository libraryRepository = new InMemoryLibraryRepository();

        LibraryRegistry libraryRegistry = new LibraryRegistry(libraryRepository, libraryInput);

        String result =
                libraryRegistry.searchByTitle().map(t -> "Search Results: " + t).orElse("No Search Results");

        System.out.println(result);

    }
}
