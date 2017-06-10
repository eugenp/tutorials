package com.baeldung.dependency.setter;

import com.baeldung.dependency.domain.User;
import com.baeldung.dependency.repository.IUserRepository;
import com.baeldung.dependency.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceSetterImpl implements IUserService {

    private IUserRepository userRepository;

    public UserServiceSetterImpl() {
    }

    @Autowired
    public void setUserRepository(final IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(final User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(final User user) {
        userRepository.deleteById(user.getId());
    }

    @Override
    public User findById(final Integer id) {
        return userRepository.findById(id);
    }

}
