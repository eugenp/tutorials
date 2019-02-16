package com.mycompany.userdetails.infrastructure;

import com.mycompany.userdetails.domain.IUserRepository;

public class UserRepostitoryImpl implements IUserRepository {
    
    public String fetchUserDetails(Long id) {
        return "some user details";
    }

}
