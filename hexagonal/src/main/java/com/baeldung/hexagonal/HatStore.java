package com.baeldung.hexagonal;

//primary port
interface HatStore {

	void add(String name, String hat);

	String getHatFor(String name);
}
