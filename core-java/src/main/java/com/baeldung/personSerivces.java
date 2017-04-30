package com.baeuldung.serialization;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class personSerivces {

	public static void main(String[] args) {
		Person p = new Person();
		p.setAge(20);
		p.setName("Joe");

		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream("yofile.txt");
			ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
			outputStream.writeObject(p);
			outputStream.close();
			System.out.println("done");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
