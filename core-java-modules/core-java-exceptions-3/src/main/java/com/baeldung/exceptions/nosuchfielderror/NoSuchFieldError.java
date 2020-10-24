package com.baeldung.exceptions.nosuchfielderror;

import java.lang.reflect.Field;

public class NoSuchFieldError {
	// String message = "Hello Baeldung!!!";

	public static String getMessage() throws ClassNotFoundException, NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		NoSuchFieldError clss = new NoSuchFieldError();
		Class<?> aClass = Class.forName(clss.getClass().getName());
		Field field = aClass.getDeclaredField("message");
		field.setAccessible(true);
		String msgStr = (String) field.get(clss);
		return msgStr;
	}

	public static String getDependentMessage() {
		return Dependent.message;
	}
}
