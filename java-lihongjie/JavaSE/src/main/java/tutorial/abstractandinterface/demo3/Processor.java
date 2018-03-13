package tutorial.abstractandinterface.demo3;

public class Processor {

	public String name() {
		return getClass().getSimpleName();
	}
	
	Object process(Object input) {
		return input;
	}
}
