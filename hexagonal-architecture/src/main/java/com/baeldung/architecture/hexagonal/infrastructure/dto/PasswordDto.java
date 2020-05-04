package com.baeldung.architecture.hexagonal.infrastructure.dto;

import com.baeldung.architecture.hexagonal.core.domain.User;
import lombok.Data;

@Data
public class PasswordDto {
    private User user;
    private String newPassword;
}
