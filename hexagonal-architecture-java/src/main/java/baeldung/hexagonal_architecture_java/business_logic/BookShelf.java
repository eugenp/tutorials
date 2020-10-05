package baeldung.hexagonal_architecture_java.business_logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import baeldung.hexagonal_architecture_java.repository.BookStoreRepository;
import baeldung.hexagonal_architecture_java.repository.H2Repository;
import baeldung.hexagonal_architecture_java.repository.PostgresRepository;

public class BookShelf implements IBookShelf{
	
	public static String H2_STORE = "h2";
	public static String POSTGRES_STORE = "postgres";

	private BookStoreRepository repository;
	public BookShelf(String store) throws SQLException, IOException{
		if(store.equals(H2_STORE))
			repository = new H2Repository();
		else if(store.equals(POSTGRES_STORE))
			repository = new PostgresRepository();
	}

	public void createBookEntry(String name, String isbn, String author
			, int numberOfCopies) throws Exception{
		try{
			repository.insertIntoBookTableString(name, isbn, author);
			repository.insertIntoCatalogTable(isbn, numberOfCopies);
		}catch(SQLException e){
			throw new Exception("This book entry has been created");
		}
	}
	
	public void sellBook(String isbn, int numberOfCopies) throws Exception {
		
	}
	
	public void addMoreCopiesOfBook(String isbn, int numberOfCopies) throws Exception{
		try{
			repository.updateCatalogTable(isbn, numberOfCopies);
		}catch(SQLException e){
			throw new Exception("An unexpected error has occurred");
		}
	}
	
	public List<Book> getBookEntries() throws Exception {
		
		try {
			return repository.getBooks();
		} catch (SQLException e) {
			throw new Exception("An unexpected error has occurred");
		}
	}
	
	public void deleteBookEntry(String isbn) throws Exception {
		try {
			repository.deleteBookEntry(isbn);
			repository.deleteCatalogEntry(isbn);
		} catch (SQLException e) {
			throw new Exception("An unexpected error has occurred");
		}
	}
}
