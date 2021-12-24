/*******************************************************************************
 *
 * Copyright 2017-2021, Bemindt Consulting Group, Inc.
 * 
 * UserRepository.java
 * rich
 * Dec 23, 2021
 * 
 *******************************************************************************/

package com.baeldung.hexagonal.repository;

import java.util.List;

import com.baeldung.hexagonal.dto.User;

public interface UserRepository {
    public List<User> getUsers();
}
