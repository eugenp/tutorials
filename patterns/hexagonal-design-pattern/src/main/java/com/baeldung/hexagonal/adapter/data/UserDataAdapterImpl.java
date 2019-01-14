package com.baeldung.hexagonal.adapter.data;

import com.baeldung.hexagonal.adapter.data.entity.UserEntity;
import com.baeldung.hexagonal.adapter.data.mapper.ModelMapper;
import com.baeldung.hexagonal.adapter.data.repository.UserRepository;
import com.baeldung.hexagonal.core.contract.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserDataAdapterImpl implements UserDataAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void addUser(UserDTO user) {
        userRepository.save(modelMapper.map(user, UserEntity.class));
    }

    public List<UserDTO> getUsers() {
        return modelMapper.mapAsList(userRepository.findAll(), UserDTO.class);
    }
}