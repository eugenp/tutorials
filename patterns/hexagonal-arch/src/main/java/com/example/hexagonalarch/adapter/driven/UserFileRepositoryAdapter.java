package com.example.hexagonalarch.adapter.driven;

import com.example.hexagonalarch.port.driven.UserRepositoryPort;
import com.example.hexagonalarch.vo.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component("userFileRepositoryAdapter")
@ConditionalOnProperty(name = "user.repository.adapter", havingValue = "file")
public class UserFileRepositoryAdapter implements UserRepositoryPort {

    @Override
    public boolean save(User user) {
        try {
            Files.write(Paths.get("new-user/user.txt"), "new-data\n".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
