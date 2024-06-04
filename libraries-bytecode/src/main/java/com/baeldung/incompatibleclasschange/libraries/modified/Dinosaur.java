package com.baeldung.incompatibleclasschange.libraries.modified;

public class Dinosaur {
    public static void species(String sp) {
	if(sp == null) {
		System.out.println("I am a generic Dinosaur");
	} else {
		System.out.println(sp);
	}
    }
}
