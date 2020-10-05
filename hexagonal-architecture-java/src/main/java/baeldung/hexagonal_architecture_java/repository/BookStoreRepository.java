package baeldung.hexagonal_architecture_java.repository;

import java.sql.SQLException;
import java.util.List;

import baeldung.hexagonal_architecture_java.business_logic.Book;

public interface BookStoreRepository {
	public void insertIntoBookTableString(String name, String isbn, String author) throws SQLException;
	public void insertIntoCatalogTable(String isbn, int numberOfCopies) throws SQLException;
	public void updateCatalogTable(String isbn, int numberOfCopies) throws SQLException;
	public void deleteBookEntry(String isbn) throws SQLException;
	public void deleteCatalogEntry(String isbn) throws SQLException;
	public List<Book> getBooks() throws SQLException;
	public int getNumberOfBooks(String isbn) throws SQLException;
}
