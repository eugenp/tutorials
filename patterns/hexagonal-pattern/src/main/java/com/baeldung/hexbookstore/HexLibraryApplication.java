package com.baeldung.hexbookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HexLibraryApplication {

	public static void main(String[] args) {
		System.setProperty("java.rmi.server.hostname","192.168.1.2");
		SpringApplication.run(HexLibraryApplication.class, args);
	}

}
