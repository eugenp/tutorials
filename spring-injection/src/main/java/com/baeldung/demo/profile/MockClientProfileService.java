package com.baeldung.demo.profile;

import org.springframework.stereotype.Component;

@Component
public class MockClientProfileService implements ClientProfileService{


    public ClientProfile findByUserId(String userId) {
        // TODO Auto-generated method stub
        return new ClientProfile("johndoe","John","Doe") ;
    }

}
