package com.example.hexagonalarch.port.driven;

import com.example.hexagonalarch.vo.User;

public interface UserRepositoryPort {
    boolean save(User user);
}
