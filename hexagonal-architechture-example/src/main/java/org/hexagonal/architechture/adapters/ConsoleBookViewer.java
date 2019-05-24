package org.hexagonal.architechture.adapters;

import org.hexagonal.architechture.core.Book;
import org.hexagonal.architechture.core.BookViewer;

public class ConsoleBookViewer implements BookViewer{

    @Override
    public void render(Book book) {
       
        System.out.println(book.getName());
        
    }
    
}
