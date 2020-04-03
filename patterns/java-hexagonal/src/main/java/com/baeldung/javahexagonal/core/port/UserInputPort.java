package com.baeldung.javahexagonal.core.port;

import com.baeldung.javahexagonal.core.domain.User;

public interface UserInputPort {
	
	User registerUser(User user) throws Exception;
	User getUserByEmail(String email);
	User updateUser(User user) throws Exception;

}
