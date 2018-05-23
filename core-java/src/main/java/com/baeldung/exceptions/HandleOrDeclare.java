import java.io.File;
import java.io.FileNotFoundException;

public class HandleOrDeclare {

	public static void main(String args[]) throws FileNotFoundException {
		ExceptionExample exceptionExample = new ExceptionExample();
		try {
			exceptionExample.readFile(new File(""));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
