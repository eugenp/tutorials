/**
 * 
 */
package com.test.example.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.test.example.port.StudentServiceAPI;
import com.test.example.port.StudentServiceAPIImpl;


public class StudentServiceConsole {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StudentServiceAPI port = new StudentServiceAPIImpl();
		try (Scanner scanner = new Scanner(System.in)) {
			boolean exit = false;
			while (!exit) {
				System.out.println("Input Details");
				System.out.println("Press 1 to enter student details");
				System.out.println("Press 2 to exit");
				String cmd = scanner.next();
				if ("1".equals(cmd)) {
					System.out.println(
							"Enter Student details. Each detail in new line. The sequence of student details is: Student Name, Student Specialization");
					// Sample input
					/*
					 * Tom 
					 * Computer Science
					 */
					List<String> tokens = new ArrayList<>();
					while (scanner.hasNext() && tokens.size() <= 2) {
						tokens.add(scanner.next());
						if (tokens.size() == 2) {
							break;
						}
					}
					exit = true;
					port.enterStudentDetails(tokens.get(0), tokens.get(1));
					System.out.println("Student Details saved successfully");
				} else if ("2".equals(cmd)) {
					exit = true;
				} else {
					System.out.println("Unknown command: " + cmd);
				}
			}
		}
	}

}
