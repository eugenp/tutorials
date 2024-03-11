package de.samples.quarkus;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Start {
	
	public static void main(String[] args) {
		Quarkus.run(args);
	}
	
}
