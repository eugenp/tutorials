package baeldung.hexagonal_architecture_java.business_logic;

import java.util.List;

public interface IBookShelf {
	public void createBookEntry(String name, String isbn, String author
			, int numberOfCopies) throws Exception;
	
	public void sellBook(String isbn, int numberOfCopies) throws Exception;
		
	
	public void addMoreCopiesOfBook(String isbn, int numberOfCopies) throws Exception;
	
	public List<Book> getBookEntries() throws Exception;
	
	public void deleteBookEntry(String isbn) throws Exception;

}
