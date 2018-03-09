package spring.scope.bean;

public class HelloMessageGenerator {

	private String message = "Hello";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public HelloMessageGenerator requestMessage() {
		
		return new HelloMessageGenerator();
	}
}
