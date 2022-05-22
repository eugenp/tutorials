package main.java.com.baeldung.tutorials;

public class MainClass {
	public static void main(String[] args) {
		System.out.println("Hello World, Sudarshan!");
		Processor processor = new Processor("Intel", "i5");
		Computer originalComputer = new Computer(1, 2048, "2022", "model1", processor);
		Computer copiedComputer = null;
		
		PersonalizedComputer originalPComputer = new PersonalizedComputer(1, 2048, "2022", "model1", processor);
		PersonalizedComputer copiedPComputer = null;

		try {
			System.out.println("================Shallow Copy================");
			System.out.println("\n---- originalComputer ----" + originalComputer);
			copiedComputer = (Computer) originalComputer.clone();
			copiedComputer.getProcessor().setCompany("Apple");
			copiedComputer.setMemory(1234); // copied primitive types will be a different copy, not referring the same value
			System.out.println("\n---- copiedComputer ----" + copiedComputer);
			System.out.println("\n---- originalComputer ----" + originalComputer);
			
			System.out.println("================Deep Copy================");
			System.out.println("\n---- originalPersonalizedComputer ----" + originalPComputer);
			copiedPComputer = (PersonalizedComputer) originalPComputer.clone();
			copiedPComputer.getProcessor().setCompany("Apple");
			System.out.println("\n---- copiedPersonalizedComputer ----" + copiedPComputer);
			System.out.println("\n---- originalPersonalizedComputer ----" + originalPComputer);
		} catch (CloneNotSupportedException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
