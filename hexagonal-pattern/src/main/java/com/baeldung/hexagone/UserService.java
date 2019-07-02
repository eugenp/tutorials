package com.baeldung.hexagone;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class UserService {
    @Autowired
    private
    CrudRepositoryImpl userRepository;


     List<User> getAllUsers(){
      return (List<User>) userRepository.findAll();
    }


    @Async
     User addUser(User user){
       return userRepository.save(user);

    }
}
