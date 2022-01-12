package com.baeldung.boot.customRepository.repository;

import com.baeldung.boot.customRepository.model.User;

public interface CustomUserRepository {

    User customFindMethod(Long id);

}
