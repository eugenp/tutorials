package com.baeldung.javahexagonal.core.port;

import com.baeldung.javahexagonal.core.domain.User;

public interface UserRepoOutputPort {

	User getUserByEmail(String email);
	User saveUser(User user);
	User updateUser(User user);
	
}
