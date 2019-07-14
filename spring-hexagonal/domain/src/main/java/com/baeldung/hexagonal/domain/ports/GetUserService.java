package com.baeldung.hexagonal.domain.ports;

import com.baeldung.hexagonal.domain.User;
import java.util.List;

public interface GetUserService {
    List<User> getAllUsers();
}
