package com.baeldung.spring.data.persistence.customrepository.repository;

import com.baeldung.spring.data.persistence.customrepository.model.User;

public interface CustomUserRepository {

    User customFindMethod(Long id);

}
