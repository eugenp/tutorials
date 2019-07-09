package com.baeldung.hexagon.dao;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.hexagon.domain.model.User;
import com.baeldung.hexagon.domain.ports.IUserAccess;

@Component
public class UserAccessImpl implements IUserAccess {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRecordRepository userRecordRepository;

	@Override
	public void createUser(User user) {
		UserRecord userRecord = modelMapper.map(user, UserRecord.class);
		userRecordRepository.save(userRecord);
	}
}
