package com.mycompany.userdetails.domain;

// UserDetails is hexagon
public class UserDetails implements IUserService {
    
    private IUserRepository userRepository;
	
    public UserDetails(IUserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    public String getUserDetails(Long id) {
        return userRepository.fetchUserDetails(id);
    }
}
