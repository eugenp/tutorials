package baeldung.hexagonal_architecture_java.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import baeldung.hexagonal_architecture_java.business_logic.Book;

public class H2Repository implements BookStoreRepository {

	private Connection connection;

	public H2Repository() throws SQLException, IOException {
		Properties credentials = new Properties();
		credentials.put("user", "sa");
		connection = DriverManager.getConnection("jdbc:h2:~/test", credentials);
		FileReader fr = new FileReader("src/main/resources/Initializer-Script.sql") ;
        BufferedReader br = new BufferedReader(fr) ;
        Statement statement = connection.createStatement();
		statement.execute(br.readLine());
		statement.close();
		br.close();
	}

	@Override
	public void insertIntoBookTableString(String name, String isbn, String author) throws SQLException {
		String insert = "INSERT INTO book (name, isbn, author) VALUES ('" + name + "', '" + isbn + "', '" + author + "');";
		Statement statement = connection.createStatement();
		statement.execute(insert);
		statement.close();
	}

	@Override
	public void insertIntoCatalogTable(String isbn, int numberOfCopies) throws SQLException {
		String insert = "INSERT INTO catalog (isbn, numberOfItems) VALUES ('" + isbn + "', " + numberOfCopies + ");";
		Statement statement = connection.createStatement();
		statement.execute(insert);
		statement.close();
	}

	@Override
	public void updateCatalogTable(String isbn, int numberOfCopies) throws SQLException {
		String update = "UPDATE catalog SET numberOfItems = numberOfItems + " + numberOfCopies +  " WHERE isbn = '" + isbn + "';";
		Statement statement = connection.createStatement();
		statement.executeUpdate(update);
		statement.close();
	}

	@Override
	public void deleteBookEntry(String isbn) throws SQLException {
		String delete = "DELETE FROM book WHERE isbn = '" + isbn + "';";
		Statement statement = connection.createStatement();
		statement.executeUpdate(delete);
		statement.close();
	}

	@Override
	public void deleteCatalogEntry(String isbn) throws SQLException {
		String delete = "DELETE FROM catalog WHERE isbn = " + isbn + ";";
		Statement statement = connection.createStatement();
		statement.executeUpdate(delete);
		statement.close();
	}

	@Override
	public List<Book> getBooks() throws SQLException{
		String fetch = "SELECT * FROM book;";
		Statement statement = connection.createStatement();
		ResultSet resultset = statement.executeQuery(fetch);
		List<Book> result = new ArrayList<Book>();
		resultset.first();
		while(resultset.isAfterLast()){
			Book book = new Book();
			book.name = resultset.getString("name");
			book.isbn = resultset.getString("isbn");
			book.author = resultset.getString("author");

			result.add(book);
			resultset.next();
		}

		statement.close();
		return result;
	}

	@Override
	public int getNumberOfBooks(String isbn) throws SQLException{
		String fetch = "SELECT numberOfItems FROM catalog WHERE isbn = '" + isbn + "';";
		Statement statement = connection.createStatement();
		ResultSet resultset = statement.executeQuery(fetch);
		if(resultset.isBeforeFirst()){
			resultset.next();
			return resultset.getInt("numberOfItems");
		}else{
			throw new SQLException("This book has no entry");
		}
	}

}
