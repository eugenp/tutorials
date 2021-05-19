package com.baeldung;

public class MyBean {

	public String addTwo(String body) {
		return body + " and two";
	}

	public String addThree(String body) {
		return body + " and three";
	}

	public MyPayload addTwo(MyPayload body) {
		body.setValue(body.getValue() + " and two");
		return body;
	}

	public MyPayload addThree(MyPayload body) {
		body.setValue(body.getValue() + " and three");
		return body;
	}
}
