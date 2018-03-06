package org.springframework.tutorial.tutorial1;

import org.springframework.beans.factory.DisposableBean;

public class HelloWorld implements DisposableBean {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void printMessage() {
		System.out.println("Your Message : " + message);
	}

	public void cleanup() {
		this.setMessage("销毁");
		System.out.println("销毁之前要调用!");
	}


	public void destroy() throws Exception {
		System.out.println("该句在销毁之前要显示!");
	}
}
