package main.java.reflection;

import java.lang.reflect.Field;

public class NoSuchFieldError2 {
	//String message = "Hello Baeldung!!!";

	public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
		print();
	}

	public static void print(){
		ValidateNoSuchFieldError clss = new ValidateNoSuchFieldError();
		Class<?> aClass = Class.forName(clss.getClass().getName());
		Field field = aClass.getDeclaredField("message");
		field.setAccessible(true);
		String msgStr = (String)field.get(clss);
		System.out.println(msgStr);
	}
}
