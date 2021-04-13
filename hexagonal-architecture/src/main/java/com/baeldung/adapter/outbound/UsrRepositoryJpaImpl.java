package com.baeldung.adapter.outbound;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.core.domain.User;
import com.baeldung.port.outbound.UserRepository;
import com.baeldung.port.outbound.jpa.UserEntity;
import com.baeldung.port.outbound.jpa.UserJpaRepository;
import com.google.common.base.Supplier;

@Repository("jpaRepo")
@Transactional
public class UsrRepositoryJpaImpl implements UserRepository {

	private static final String NUSER_PRESENT_FOR_THE_GIVEN_ID = "No user present for the given id.";
	@Autowired
	private UserJpaRepository userJpaRepository;

	@Override
	public String addNewUser(User user) {
		return userJpaRepository.save(converter(user, UserEntity::new)).getId().toString();
	}

	@Override
	public User getUser(String id) {
		return converter(userJpaRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException(NUSER_PRESENT_FOR_THE_GIVEN_ID)), User::new);
	}

	@Override
	public boolean removeUser(String id) {
		userJpaRepository.deleteById(UUID.fromString(id));
		return Boolean.TRUE;
	}

	@Override
	public Collection<User> getAllUser() {
		return userJpaRepository.findAll().stream().map(entity -> converter(entity, User::new))
				.collect(Collectors.toList());
	}

	private <T> T converter(Object src, Supplier<T> supplier) {
		T target = supplier.get();
		BeanUtils.copyProperties(src, target);
		if (src instanceof UserEntity)
			((User) target).setId(((UserEntity) src).getId().toString());
		return target;
	}
}
