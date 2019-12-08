package com.baeldung.hexagon.internal;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.baeldung.hexagon.port.IGetBook;
import com.baeldung.hexagon.port.IOutput;

/**
 * The command handler for displaying a random Book 
 * associated with AskBook command.
 *
 */
public class DisplayRandomBook implements Consumer<AskBook> {
	private IGetBook bookFetcher;
	private RandomBookPicker randomBookPicker;
	private IOutput output;

	public DisplayRandomBook(IGetBook bookFetcher, IOutput output) {
		this.bookFetcher = bookFetcher;
		this.randomBookPicker = new RandomBookPicker();
		this.output = output;
	}

	public void accept(AskBook askBook) {
		List<Book> poems = getBooks(askBook);
		Optional<Book> poem = getAnyBook(poems);
		writeLines(poem);		
	}

	private List<Book> getBooks(AskBook askBook) {
		String language = askBook.getAuthor();
		String[] poems = bookFetcher.getBook(language);
		List<Book> bookObjects = 
			Arrays.stream(poems)
				.map(Book::new)
				.collect(Collectors.toList());
		return bookObjects;
	}
	
	private Optional<Book> getAnyBook(List<Book> bookList) {
		return randomBookPicker.pickBook(bookList);
	}
	
	private void writeLines(Optional<Book> book) {
		book.ifPresent(x -> output.output(x.getVerses()));
	}
}
