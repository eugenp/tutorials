package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.demo.impl.Student;
import com.demo.impl.Student1;

@SpringBootApplication
public class ServerApp {

	public static void main(String[] args) throws Exception {
		final ApplicationContext context = SpringApplication.run(ServerApp.class, args);

		System.err.println(
				":::::::::::::::::::::::::::::::::::::::::::: Constructor Bean injection ::::::::::::::::::::::::::::::::::::::::::::");
		final Student student = context.getBean(Student.class);
		student.getSchool().info();

		System.err.println(
				":::::::::::::::::::::::::::::::::::::::::::: Setter Bean injection ::::::::::::::::::::::::::::::::::::::::::::");
		final Student1 student1 = context.getBean(Student1.class);
		student1.schoolInfo();
	}
}