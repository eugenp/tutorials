package com.baeldung.typesbeaninjection.constructorbased;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    String findOne(){
        return "User";
    }

}
