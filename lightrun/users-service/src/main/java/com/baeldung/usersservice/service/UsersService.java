/****************************************************************************************************************
 *
 *  Copyright (c) 2022 OCLC, Inc. All Rights Reserved.
 *
 *  OCLC proprietary information: the enclosed materials contain
 *  proprietary information of OCLC, Inc. and shall not be disclosed in whole or in
 *  any part to any third party or used by any person for any purpose, without written
 *  consent of OCLC, Inc.  Duplication of any portion of these materials shall include this notice.
 *
 ******************************************************************************************************************/

package com.baeldung.usersservice.service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.usersservice.adapters.jms.JmsSender;
import com.baeldung.usersservice.adapters.repository.UserRecord;
import com.baeldung.usersservice.adapters.repository.UsersRepository;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JmsSender jmsSender;

    public UserRecord getUserById(String id) {
        return usersRepository.findById(id)
            .orElseThrow(() -> new UnknownUserException(id));
    }

    @Transactional
    public void deleteUserById(String id) {
        var user = usersRepository.findById(id)
            .orElseThrow(() -> new UnknownUserException(id));
        usersRepository.delete(user);

        jmsSender.sendDeleteUserMessage(id);
    }

    @Transactional
    public UserRecord updateUser(String id, Optional<String> newName) {
        var user = usersRepository.findById(id)
            .orElseThrow(() -> new UnknownUserException(id));

        newName.ifPresent(user::setName);

        return user;
    }

    public UserRecord createUser(String name) {
        var user = new UserRecord(UUID.randomUUID()
            .toString(), name);
        usersRepository.save(user);
        return user;
    }
}
