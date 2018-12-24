package com.baeldung.hexagonal;

public interface UserDataBasePort {
    User getUserById(long idUser);
}
