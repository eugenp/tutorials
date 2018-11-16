package com.baeldung.string;

public class AddingNewLineToString {
	
	public static void main(String[] args) {
		String line1 = "Humpty Dumpty sat on a wall.";
		String line2 = "Humpty Dumpty had a great fall.";
		String rhyme = "";
		
		System.out.println("***New Line in a String in Java***");
		//1. Using "\n"
		System.out.println("1. Using \\n");
		rhyme = line1 + "\n" + line2;
		System.out.println(rhyme);
		
		//2. Using "\r\n"
		System.out.println("2. Using \\r\\n");
		rhyme = line1 + "\r\n" + line2;
		System.out.println(rhyme);
		
		//3. Using "\r"
		System.out.println("3. Using \\r");
		rhyme = line1 + "\r" + line2;
		System.out.println(rhyme);
		
		//4. Using "\n\r" Note that this is not same as "\r\n"
		//   Using "\n\r" is equivalent to adding two lines
		System.out.println("4. Using \\n\\r");
		rhyme = line1 + "\n\r" + line2;
		System.out.println(rhyme);
		
		//5. Using System.lineSeparator()
		System.out.println("5. Using System.lineSeparator()");
		rhyme = line1 + System.lineSeparator() + line2;
		System.out.println(rhyme);
		
		//6. Using System.getProperty("line.separator")
		System.out.println("6. Using System.getProperty(\"line.separator\")");
		rhyme = line1 + System.getProperty("line.separator") + line2;
		System.out.println(rhyme);
		
		System.out.println("***HTML to rendered in a browser***");
		//1. Line break for HTML using <br>
		System.out.println("1. Line break for HTML using <br>");
		rhyme = line1 + "<br>" + line2;
		System.out.println(rhyme);
		
		//2. Line break for HTML using “&#10;”
		System.out.println("2. Line break for HTML using &#10;");
		rhyme = line1 + "&#10;" + line2;
		System.out.println(rhyme);
		
		//3. Line break for HTML using “&#13;”
		System.out.println("3. Line break for HTML using &#13;");
		rhyme = line1 + "&#13;" + line2;
		System.out.println(rhyme);
		
		//4. Line break for HTML using “&#10&#13;;”
		System.out.println("4. Line break for HTML using &#10;&#13;");
		rhyme = line1 + "&#10;&#13;" + line2;
		System.out.println(rhyme);
		
		//5. Line break for HTML using \n”
		System.out.println("5. Line break for HTML using \\n");
		rhyme = line1 + "\n" + line2;
		System.out.println(rhyme);
	}

}