package org.baeldung.persistence.service;

import javax.transaction.Transactional;

import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.dao.VerificationTokenRepository;
import org.baeldung.persistence.model.Role;
import org.baeldung.persistence.model.User;
import org.baeldung.persistence.model.VerificationToken;
import org.baeldung.validation.service.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
	@Autowired
	private UserRepository repository;
	// NOV 6
	@Autowired
	private VerificationTokenRepository tokenRepository;

	@Transactional
	@Override
	public User registerNewUserAccount(UserDto accountDto)
			throws EmailExistsException {
		if (emailExist(accountDto.getEmail())) {
			throw new EmailExistsException(
					"There is an account with that email adress: "
							+ accountDto.getEmail());
		}
		User user = new User();
		user.setFirstName(accountDto.getFirstName());
		user.setLastName(accountDto.getLastName());
		user.setPassword(accountDto.getPassword());
		user.setEmail(accountDto.getEmail());
		user.setRole(new Role(Integer.valueOf(1), user));
		return repository.save(user);
	}

	private boolean emailExist(String email) {
		User user = repository.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public User getRegisteredUser(String email) {

		User user = repository.findByEmail(email);
		return user;

	}

	@Override
	public User getUser(String verificationToken) {
		User user = tokenRepository.findByToken(verificationToken).getUser();
		return user;
	}

	@Transactional
	@Override
	public void verifyRegisteredUser(User user) {
		repository.save(user);
	}

	@Transactional
	@Override
	public void addVerificationToken(User user, String token) {
		VerificationToken myToken = new VerificationToken(token, user);
		user.setVerificationToken(myToken);
		repository.save(user);
	}
}
