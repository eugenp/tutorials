package com.baeldung.tutorials.bookkeeper;

import org.apache.bookkeeper.client.BookKeeper;

public class Main {
	
	public static void main(String args[]) {
	
	    BookKeeper bk = BkHelper.createBkClient(args[0]);
	    System.out.println("Connect OK");
	    
	}
}