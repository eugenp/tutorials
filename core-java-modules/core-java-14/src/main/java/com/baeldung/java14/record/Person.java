package com.baeldung.java14.record;

import java.util.Objects;

public record Person (String name, String address) {
	
	  public static String UNKNOWN_ADDRESS = "Unknown";
	  public static String UNNAMED = "Unnamed";

	  public Person {
		    Objects.requireNonNull(name);
		    Objects.requireNonNull(address);
	  }
	
	  public Person(String name) {
		    this(name, UNKNOWN_ADDRESS);
	  }
	
	  public static Person unnamed(String address) {
		    return new Person(UNNAMED, address);
	  }
}