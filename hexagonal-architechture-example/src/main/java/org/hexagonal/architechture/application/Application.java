package org.hexagonal.architechture.application;

import org.hexagonal.architechture.adapters.ConsoleBookViewer;
import org.hexagonal.architechture.adapters.InMemoryBookRepository;
import org.hexagonal.architechture.core.BookGallery;

public class Application {
    
    public static void main(String[] args) {
        BookGallery bookGallery = new BookGallery(new ConsoleBookViewer(), new InMemoryBookRepository());
        bookGallery.displayAllBooks();
    }

}
