package com.baeldung.jmh.warmup;

public class MainApplication {

	static long start;
	static long end;

	static {
		ManualClassLoader.load();
	}

	public static void main(String[] args) {
		start = System.nanoTime();
		ManualClassLoader.load();
		end = System.nanoTime();
		System.out.println("Total time taken : " + (end - start));
	}

}
