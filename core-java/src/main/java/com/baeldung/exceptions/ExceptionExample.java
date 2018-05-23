import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ExceptionExample {

	public static void main(String args[]) throws FileNotFoundException {
		method();
		readFile(new File(""));

	}

	public static void method() {
		getEmployees3();
	}

	public static void getEmployees() {
		String[] employees = { "David", "Eugen", "Josh" };
		System.out.println(employees[1]);

	}

	public static void readFile(File file) throws FileNotFoundException {
		if (file != null) {
			System.out.println("File is located at: " + file.getAbsolutePath());
		} else {
			throw new NullPointerException();
		}
	}

	public static void readFile2() {
		try (Scanner scanner = new Scanner(new File("someFile"))) {
			System.out.println(scanner.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void getEmployees2() {
		String[] employees = { "David", "Eugen", "Josh" };

		int position = 8;
		if (position > 0 && position < employees.length) {
			System.out.println(employees[position]);
		}
	}

	public static void getEmployees3() {
		String[] employees = { "David", "Eugen", "Josh" };
		try {
			System.out.println(employees[8]);
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} finally {
			System.out.println("I'll execute in any event!");
		}
	}

	public static void multipleCatch() {
		FileInputStream f = null;
		try {
			f = new FileInputStream("someFile");
			f.read();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void multipleCatch2() {
		FileInputStream f = null;
		try {
			f = new FileInputStream("someFile");
			f.read();
		} catch (FileNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

}
