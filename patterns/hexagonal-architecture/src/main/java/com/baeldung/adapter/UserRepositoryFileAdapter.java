package com.baeldung.adapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.baeldung.domain.User;
import com.baeldung.port.UserRepositoryPort;

public class UserRepositoryFileAdapter implements UserRepositoryPort {

	private static Path usersFile;
	static{
		try {
			usersFile = Files.writeString(Files.createTempFile("users", ".txt"), "Gage,Dudley,vel.venenatis@milacinia.com,1967-10-27\nAbdul,Bowen,elementum@ullamcorpereueuismod.edu,1977-04-01");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Optional<Collection<User>> findUsersBornOn(int monthValue, int dayOfMonth) {
		List<User> users = new ArrayList<>();
		try {
			String s = Files.readString(usersFile);
			String lines[] = s.split("\\r?\\n");
			
			for(String line : lines) {
				String[] u = line.split(",");
				
				User user = new User(u[0], u[1], u[2], LocalDate.parse(u[3]));
				if(user.isBirthday(monthValue, dayOfMonth)) {
					users.add(user);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.of(users);
	}

}
