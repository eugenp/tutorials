package com.mycompany.userdetails.domain;

// IUserRepository is right side port
public interface IUserRepository {
    String fetchUserDetails(Long id);
}
