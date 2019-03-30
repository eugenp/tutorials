package com.baeldung.hexagon;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.domain.User;
import com.baeldung.port.NotifyPort;
import com.baeldung.port.UserRepositoryPort;

public class BirthdayService {

	@Autowired
	private UserRepositoryPort userRepositoryPort;
	
	@Autowired
	private NotifyPort notifyPort;
	
	public void wishHappyBirthday(LocalDate birthDay) {
		Optional<Collection<User>> users = userRepositoryPort.findUsersBornOn(birthDay.getMonthValue(), birthDay.getDayOfMonth());
		users.ifPresent(uc -> uc.stream()
								.forEach(u -> notifyPort.notify("Happy birthday!", getWishMessage(u))
						));
	}
	
	private String getWishMessage(User u) {
		return MessageFormat.format("Happy birthday {0}!", u.getName());
	}
}
