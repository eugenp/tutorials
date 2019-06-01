package com.example.hexagonalarch.adapter.driven;

import com.example.hexagonalarch.port.driven.UserRepositoryPort;
import com.example.hexagonalarch.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("userDbRepositoryAdapter")
@ConditionalOnProperty(name = "user.repository.adapter", havingValue = "db")
public class UserDbRepositoryAdapter implements UserRepositoryPort {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(User user) {
        int count = jdbcTemplate.update("INSERT INTO USER (name, age) VALUES (?,?)", user.getName(), user.getAge());
        return count == 1;
    }
}
