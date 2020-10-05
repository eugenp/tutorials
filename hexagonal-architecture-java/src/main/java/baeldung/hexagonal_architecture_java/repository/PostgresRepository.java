package baeldung.hexagonal_architecture_java.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import baeldung.hexagonal_architecture_java.business_logic.Book;

public class PostgresRepository implements BookStoreRepository {

	private Connection connection;

	public PostgresRepository() throws SQLException {
		Properties credentials = new Properties();
		credentials.put("user", "postgres");
		credentials.put("password", "123456");
		connection = DriverManager.getConnection("jdbc:postgresql:postgres", credentials);
	}

	@Override
	public void insertIntoBookTableString(String name, String isbn, String author) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertIntoCatalogTable(String isbn, int numberOfCopies) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCatalogTable(String isbn, int numberOfCopies) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBookEntry(String isbn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCatalogEntry(String isbn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Book> getBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfBooks(String isbn) {
		// TODO Auto-generated method stub
		return 0;
	}

}
