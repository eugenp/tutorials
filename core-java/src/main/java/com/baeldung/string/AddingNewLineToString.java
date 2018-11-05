package com.baeldung.string;

public class AddingNewLineToString {
	
	public static void main(String[] args) {
		String line1 = "Humpty Dumpty sat on a wall.";
		String line2 = "Humpty Dumpty had a great fall.";
		String para = "";
		
		//1. Using "\n"
		System.out.println("1. Using \\n");
		para = line1+"\n"+line2;
		System.out.println(para);
		
		//2. Using "\r\n"
		System.out.println("2. Using \\r\\n");
		para = line1+"\r\n"+line2;
		System.out.println(para);
		
		//3. Using "\r"
		System.out.println("3. Using \\r");
		para = line1+"\r"+line2;
		System.out.println(para);
		
		//4. Using "\n\r" Note that this is not same as "\r\n"
		//   Using "\n\r" is equivalent to adding two lines
		System.out.println("4. Using \\n\\r");
		para = line1+"\n\r"+line2;
		System.out.println(para);
		
		//5. Using System.lineSeparator()
		System.out.println("5. Using System.lineSeparator()");
		para = line1+System.lineSeparator()+line2;
		System.out.println(para);
		
		//6. Using System.getProperty("line.separator")
		System.out.println("6. Using System.getProperty(\"line.separator\")");
		para = line1+System.getProperty("line.separator")+line2;
		System.out.println(para);
		
		//Line break for HTML using <br>
		System.out.println("Line break for HTML using <br>");
		para = line1+"<br>"+line2;
		
		//Line break for HTML when string is in <textarea> tag
		para = line1+"&#10;"+line2;
		para = line1+"&#13;"+line2;
		
		//Line break for HTML when string is in <pre> tag
		para = line1+"&#10;"+line2;
		para = line1+"&#10;&#13;"+line2;
		para = line1+"<br>"+line2;			
	}

}
