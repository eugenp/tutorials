package com.baeldung;

public class MyBean {

	public MyPayload addTwo(MyPayload body) {
		body.setValue(body.getValue() + " and two");
		return body;
	}

	public MyPayload addThree(MyPayload body) {
		body.setValue(body.getValue() + " and three");
		return body;
	}
}
