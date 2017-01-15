package com.baeldung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {
    
	public AppTest(String testName) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( AppTest.class );
    }

    public void testApp() throws IOException {
    
    	URL url;
    	HttpURLConnection conn;
    	BufferedReader br;
    	String output;
    	
    	
    	url = new URL("http://localhost:8080/add/IPHONE/750");
    	conn = (HttpURLConnection) url.openConnection();
    	conn.setRequestMethod("POST");
		br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
    	
    	System.out.println("***************ADDED TO INVERTORY***********************");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
		System.out.println("********************************************************");
		
		url = new URL("http://localhost:8080/add/HTC/550");
    	conn = (HttpURLConnection) url.openConnection();
    	conn.setRequestMethod("POST");
		br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
    	
    	System.out.println("***************ADDED TO INVERTORY***********************");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
		System.out.println("********************************************************");
		
    	url = new URL("http://localhost:8080/view");
		conn = (HttpURLConnection) url.openConnection();		
		conn.setRequestMethod("GET");
		br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		System.out.println("***************CURRENT INVERTORY***********************");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
		System.out.println("********************************************************");
		
    	url = new URL("http://localhost:8080/view/1");
		conn = (HttpURLConnection) url.openConnection();		
		conn.setRequestMethod("GET");
		br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		System.out.println("*******************fETCHED ITEM*************************");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
		System.out.println("********************************************************");
		
		conn.disconnect();
		
    }
    
}
