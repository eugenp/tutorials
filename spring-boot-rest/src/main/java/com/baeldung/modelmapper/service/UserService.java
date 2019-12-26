package com.baeldung.modelmapper.service;

import org.springframework.stereotype.Service;

import com.baeldung.modelmapper.model.Preference;
import com.baeldung.modelmapper.model.User;

@Service
public class UserService implements IUserService {

    @Override
    public User getCurrentUser() {
        
        Preference preference = new Preference();
        preference.setId(1L);
        preference.setTimezone("Asia/Calcutta");
        
        User user = new User();
        user.setId(1L);
        user.setName("Micheal");
        user.setPreference(preference);
                
        return user;
    }
}