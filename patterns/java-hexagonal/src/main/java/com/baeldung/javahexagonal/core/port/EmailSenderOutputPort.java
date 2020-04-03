package com.baeldung.javahexagonal.core.port;

import java.io.IOException;

import com.baeldung.javahexagonal.core.domain.User;

public interface EmailSenderOutputPort {

	public void sendRegisterEmail(User user) throws IOException;
	
}
