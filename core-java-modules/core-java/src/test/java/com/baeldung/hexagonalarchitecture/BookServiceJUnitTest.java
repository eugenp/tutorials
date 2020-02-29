package com.baeldung.hexagonalarchitecture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.hexagonalarchitecture.Book;
import com.baeldung.hexagonalarchitecture.BookGenre;
import com.baeldung.hexagonalarchitecture.BookRepository;
import com.baeldung.hexagonalarchitecture.BookService;
import com.baeldung.hexagonalarchitecture.InMemoryBookRepository;


public class BookServiceJUnitTest {
    
	private BookService bookService;
	private BookRepository bookRepository;
	
	@Before
	public void setup() {
    	bookRepository = new InMemoryBookRepository();
    	bookService = new BookService(bookRepository);
    	
    	Book imaginaryField = new Book(1, "Imaginary Friend", BookGenre.HORROR, 10);
    	Book theGirlInRed = new Book(2, "The Girl in Red", BookGenre.HORROR, 25);
    	Book bookLove = new Book(3, "Book Love", BookGenre.COMIC, 3);
    	Book romeoAndJuliet = new Book(4, "Romeo and Juliet", BookGenre.DRAMA, 7);
    	Book blueMoon = new Book(5, "Blue Moon", BookGenre.ACTION, 65);
    	
    	bookService.saveBook(imaginaryField);
    	bookService.saveBook(theGirlInRed);
    	bookService.saveBook(bookLove);
    	bookService.saveBook(romeoAndJuliet);
    	bookService.saveBook(blueMoon);
	}
	
	@Test
    public void mostPopularGenreShoudBeHorror() {
    	BookGenre mostPopularGenre = bookService.getMostPopularGenre();
		
    	assertEquals(BookGenre.HORROR , mostPopularGenre);
    }
	
	@Test
    public void mostReadBookShoudBeBlueMoon() {
    	Book mostReadBook = bookService.getMostReadBook();
		
    	assertEquals(Integer.valueOf(5), mostReadBook.getId());
    }
	
	@Test
	public void aggingTwoDramaBooksShouldMakeDramaTheMostPopularGenre() {
		Book theTempest = new Book(6, "The tempes", BookGenre.DRAMA, 15);
		Book othello = new Book(7, "Othello", BookGenre.DRAMA, 31);
		
		bookService.saveBook(theTempest);
		bookService.saveBook(othello);
		
		BookGenre mostPopularGenre = bookService.getMostPopularGenre();
		
		assertNotEquals(BookGenre.HORROR , mostPopularGenre);
		assertEquals(BookGenre.DRAMA , mostPopularGenre);	
	}
}