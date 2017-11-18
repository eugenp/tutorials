package com.baeldung;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;

import com.baeldung.domain.Book;
import com.baeldung.service.LibraryService;

public class Router {
    
    public static void main( String[] args ){
    	
    	port(8080);
        
    	before((request, response) -> {
    		
    		//do some filtering stuff
    		
    	});
    	
    	after((request, response) -> {
    		response.type("application/json");
    	});
    	
    	get("ListOfBooks", (request, response) -> {
        	return LibraryService.view();
        });
    	
    	get("SearchBook/:title", (request, response) -> {
        	return LibraryService.view(request.params("title"));
        });
    	
    	post("AddBook/:title/:author/:publisher", (request, response) -> {
    		Book book = new Book();
    		book.setTitle(request.params("title"));
    		book.setAuthor(request.params("author"));
    		book.setPublisher(request.params("publisher"));
        	return LibraryService.add(book);
        });
    	
    	delete("DeleteBook/:title", (request, response) -> {
        	return LibraryService.delete(request.params("title"));
        });
    	
    }
}
