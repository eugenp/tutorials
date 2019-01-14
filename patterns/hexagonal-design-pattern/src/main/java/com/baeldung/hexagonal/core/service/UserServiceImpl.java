package com.baeldung.hexagonal.core.service;

import com.baeldung.hexagonal.adapter.data.UserDataAdapter;
import com.baeldung.hexagonal.core.contract.UserService;
import com.baeldung.hexagonal.core.contract.dto.UserDTO;
import com.baeldung.hexagonal.core.mapper.CoreModelMapper;
import com.baeldung.hexagonal.core.contract.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDataAdapter userDataAdapter;

    @Autowired
    private CoreModelMapper coreModelMapper;

    public void addUser(User user) {
        userDataAdapter.addUser(coreModelMapper.map(user, UserDTO.class));
    }

    public List<User> getUsers() {
        return coreModelMapper.mapAsList(userDataAdapter.getUsers(), User.class);
    }
}