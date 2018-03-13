package tutorial.abstractandinterface.demo4;

public abstract class StringProcessor implements Processor {

	public static String s = "Test";
	public static void main(String[] args) {
		Apply.process(new Upcase(), s);
	}
}
