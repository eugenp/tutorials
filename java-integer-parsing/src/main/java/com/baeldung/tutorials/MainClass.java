package main.java.com.baeldung.tutorials;

public class MainClass {
	public static void main(String[] args) {
		
        System.out.println(Integer.parseInt("11"));
        System.out.println(Integer.parseInt("+11"));
        System.out.println(Integer.parseInt("-11"));
        System.out.println(Integer.parseInt("11", 16));
        System.out.println(Integer.parseInt("A", 16));
        System.out.println(Integer.parseInt("B", 16));
        System.out.println(Integer.parseInt("7", 8));
        System.out.println(Integer.parseInt("5", 8));
        System.out.println(Integer.parseInt("ABCDEFG", 1, 4, 36));
        System.out.println(Integer.parseInt("abcdefg", 1, 6, 36));
        
        System.out.println(Integer.valueOf("11"));
        System.out.println(Integer.valueOf("+11"));
        System.out.println(Integer.valueOf("-11"));
        System.out.println(Integer.valueOf(11));
        System.out.println(Integer.valueOf(+11));
        System.out.println(Integer.valueOf(-11));
        System.out.println(Integer.valueOf("11", 16));
        System.out.println(Integer.valueOf("A", 16));
        System.out.println(Integer.valueOf("B", 16));
        System.out.println(Integer.valueOf("7", 8));
        System.out.println(Integer.valueOf("5", 8)); 
        System.out.println(Integer.valueOf('A')); // Unicode value
        // System.out.println(Integer.valueOf("A")); //RuntimeException 
	}
}
