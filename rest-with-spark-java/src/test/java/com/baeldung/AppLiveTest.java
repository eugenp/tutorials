package com.baeldung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.baeldung.domain.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppLiveTest extends TestCase {

	ObjectMapper mapper = new ObjectMapper();

    public AppLiveTest( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( AppLiveTest.class );
    }

    public void testApp() throws IOException, ClassNotFoundException {
    
    	URL url;
    	HttpURLConnection conn;
    	BufferedReader br;
    	String output;
    	StringBuffer resp;
    	Book book;
    	Book temp;
    	
    	url = new URL("http://localhost:8080/AddBook/Odessy/YannMartel/GreenLeaves");
    	conn = (HttpURLConnection) url.openConnection();
    	conn.setRequestMethod("POST");
    	conn.getContent();
		br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
		resp = new StringBuffer();
		
		while ((output = br.readLine()) != null) {
			resp.append(output);
		}
		
		book = mapper.readValue(resp.toString(), Book.class);
		temp = new Book("Odessy","YannMartel","GreenLeaves");
		
		assertEquals(book, temp);
		
		url = new URL("http://localhost:8080/AddBook/Twilight/StephenieMeyer/LittleBrown");
    	conn = (HttpURLConnection) url.openConnection();
    	conn.setRequestMethod("POST");
		br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
		resp = new StringBuffer();
		
		while ((output = br.readLine()) != null) {
			resp.append(output);
		}
		
		book = mapper.readValue(resp.toString(), Book.class);
		temp = new Book("Twilight","StephenieMeyer","LittleBrown");
		
		assertEquals(book, temp);
		
    	url = new URL("http://localhost:8080/ListOfBooks");
		conn = (HttpURLConnection) url.openConnection();		
		conn.setRequestMethod("GET");
		br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
		resp = new StringBuffer();
		
		while ((output = br.readLine()) != null) {
			resp.append(output);
		}
		
		List<Book> books = new ArrayList<Book>();
		
		books.add(new Book("Odessy","YannMartel","GreenLeaves"));
		books.add(new Book("Twilight","StephenieMeyer","LittleBrown"));
		
		List<Book> listOfBooks = mapper.readValue(resp.toString(), new TypeReference<List<Book>>(){});
		
		assertEquals(books, listOfBooks);
		
    	url = new URL("http://localhost:8080/SearchBook/Twilight");
		conn = (HttpURLConnection) url.openConnection();		
		conn.setRequestMethod("GET");
		br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
		resp = new StringBuffer();
		
		while ((output = br.readLine()) != null) {
			resp.append(output);
		}
		
		book = mapper.readValue(resp.toString(), Book.class);
		temp = new Book("Twilight","StephenieMeyer","LittleBrown");
		
		assertEquals(book, temp);
		
		url = new URL("http://localhost:8080/DeleteBook/Twilight");
    	conn = (HttpURLConnection) url.openConnection();
    	conn.setRequestMethod("DELETE");
		br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
    	resp = new StringBuffer();
		
		while ((output = br.readLine()) != null) {
			resp.append(output);
		}
		
		book = mapper.readValue(resp.toString(), Book.class);
		temp = new Book("Twilight","StephenieMeyer","LittleBrown");
		
		assertEquals(book, temp);
		
    	url = new URL("http://localhost:8080/ListOfBooks");
		conn = (HttpURLConnection) url.openConnection();		
		conn.setRequestMethod("GET");
		br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
		resp = new StringBuffer();
		
		while ((output = br.readLine()) != null) {
			resp.append(output);
		}
		
		books = new ArrayList<Book>();
		
		books.add(new Book("Odessy","YannMartel","GreenLeaves"));		
		listOfBooks = mapper.readValue(resp.toString(), new TypeReference<List<Book>>(){});
		
		assertEquals(books, listOfBooks);
		
		conn.disconnect();
		
    }
    
}
