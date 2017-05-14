package com.baeldung.injection;

import org.springframework.stereotype.Repository;

@Repository
class UserDAO {


    @Override
    public String toString() {
        return "UserDAO";
    }
}
