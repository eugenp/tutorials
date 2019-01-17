package com.baeldung.hexagonal.repositories.adapters;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.models.User;
import com.baeldung.hexagonal.repositories.UserRepositoryPort;

@Repository
public class FileSystemUserRepositoryAdapter implements UserRepositoryPort {

	private static final String filepath = "\\var\\temp\\data";

	@Override
	public boolean addUser(User user) {

		try {
			FileOutputStream fileOut = new FileOutputStream(filepath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(user);
			objectOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

}
