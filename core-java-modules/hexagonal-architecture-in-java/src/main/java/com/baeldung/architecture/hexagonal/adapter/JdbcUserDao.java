package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.domain.user.User;
import com.baeldung.architecture.hexagonal.port.UserDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Types;

@Component
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createUser(User user) {
        jdbcTemplate.update("insert into users values(?, ?, ?)",
                new Object[] { user.getUserId(), user.getFirstName(), user.getLastName() },
                new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
    }
}
